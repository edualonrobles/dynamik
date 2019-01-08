package com.edualonso.dynamik;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.edualonso.dynamik.activities.MainActivity;
import com.edualonso.dynamik.fragments.AddTeamFragment;
import com.edualonso.dynamik.fragments.ChangeData;
import com.edualonso.dynamik.fragments.ChangePass;
import com.edualonso.dynamik.fragments.DetailFragment;
import com.edualonso.dynamik.fragments.DetailGameFragment;
import com.edualonso.dynamik.fragments.DetailTeamFragment;
import com.edualonso.dynamik.fragments.GameFragment;
import com.edualonso.dynamik.fragments.HomeFragment;
import com.edualonso.dynamik.fragments.ListPersonFragment;
import com.edualonso.dynamik.fragments.MyTeamsFragment;
import com.edualonso.dynamik.fragments.PhotosTeamFragment;
import com.edualonso.dynamik.fragments.ProfileFragment;
import com.edualonso.dynamik.fragments.RankingFragment;
import com.edualonso.dynamik.fragments.TwitterTeamFragment;
import com.edualonso.dynamik.model.Game;
import com.edualonso.dynamik.model.Player;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.Competition;
import com.edualonso.dynamik.model.user.User;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by edu_g on 13/07/2017.
 */

/**
 * Esta clase se utiliza como Handler para la navegación dentro de la aplicación
 */

public class NavController implements FragNavController.RootFragmentListener, FragNavController.TransactionListener {


    public static final int INDEX_HOME = 0;
    public static final int INDEX_TEAM = 1;
    public static final int INDEX_BET = 2;
    public static final int INDEX_PROFILE = 3;

    private static NavController mNavController = null;
    private FragNavController mFragNavController;
    private FragmentManager fManager;
    private User u;
    private List<Fragment> fragments;
    private static AlertDialog.Builder builder;
    private static BottomNavigationView bottomBar;
    private static Activity activity;

    private FragNavController.TransactionListener onFragmentTransactionListener;

    public static NavController getInstance() {
        return mNavController;
    }

    public static void init(FragmentManager fm, Context context, BottomNavigationView btBar, Activity temp) {
        mNavController = new NavController(fm);
        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        bottomBar = btBar;
        activity = temp;
    }

    public void setOnFragmentTransactionListener(FragNavController.TransactionListener onFragmentTransactionListener) {
        this.onFragmentTransactionListener = onFragmentTransactionListener;
    }

    private NavController(FragmentManager fragmentManager) {

        SharedPreferences prefs = MainActivity.prefs;

        u = new User();
        u.setId(prefs.getString("id", ""));
        u.setName(prefs.getString("user", ""));
        u.setEmail(prefs.getString("email", ""));
        u.setPass(prefs.getString("pass", ""));
        u.setPhoto(Integer.valueOf(prefs.getString("photo", "")));


        fManager = fragmentManager;
        fragments = new ArrayList<>();

        //--------------------- HOME ------------------------------------------

        fragments.add(HomeFragment.Companion.newInstance(new HomeFragment.MyActivityListener() {
                                                             @Override
                                                             public void onClick() {
                                                                 push(AddTeamFragment.newInstance(new AddTeamFragment.MyActivityListener() {
                                                                     @Override
                                                                     public void goTeamDetail(Team t) {
                                                                         push(DetailTeamFragment.newInstance(t));
                                                                     }
                                                                 }));
                                                             }

                                                             @Override
                                                             public void onGameClicked(Game game) {
                                                                 push(DetailGameFragment.newInstance(game));
                                                             }
                                                         }
        ));


        //------------------------ MyTeamsFragment -----------------------------

        fragments.add(MyTeamsFragment.newInstance(new MyTeamsFragment.MyActivityListener() {
            @Override
            public void onClickGames(Team t) {
                push(GameFragment.newInstance(t, new GameFragment.MyActivityListener() {
                    @Override
                    public void goGameDetail(Game g) {
                        push(DetailGameFragment.newInstance(g));
                    }
                }));
            }

            @Override
            public void onClickMembers(Team t) {
                push(ListPersonFragment.newInstance(t, new ListPersonFragment.MyActivityListener() {
                    @Override
                    public void goPersonDetail(Player p) {
                        push(DetailFragment.newInstance(p,activity));
                    }
                }));
            }

            @Override
            public void onClickPhotos(Team t) {
                push(PhotosTeamFragment.Companion.newInstance(t));
            }

            @Override
            public void onClickRanking(Competition comp) {
                push(RankingFragment.newInstance(comp));
            }

            @Override
            public void onClickTwitter(Team t) {
                push(TwitterTeamFragment.newInstance(t));
            }
        }));


        //----------------------------------- Game -----------------------------------

        fragments.add(GameFragment.newInstance(new GameFragment.MyActivityListener() {
                                                   @Override
                                                   public void goGameDetail(Game g) {
                                                       push(DetailGameFragment.newInstance(g));
                                                   }
                                               }
        ));

        fragments.add(ProfileFragment.newInstance(new ProfileFragment.MyActivityListener() {
            public void onClickChangeData() {
                push(ChangeData.newInstance(u));
            }

            public void onClickChangePass() {
                push(ChangePass.newInstance(u));
            }
        }));
        FragNavController.Builder builder = FragNavController.newBuilder(null, fragmentManager, R.id.main_container);
        mFragNavController = builder.rootFragments(fragments).rootFragmentListener(this, fragments.size())
                .transactionListener(this).build();
    }


    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_HOME:
                return fragments.get(INDEX_HOME);
            case INDEX_TEAM:
                return fragments.get(INDEX_TEAM);
            case INDEX_PROFILE:
                return fragments.get(INDEX_PROFILE);
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    public void onBackPressed() {

        if (mFragNavController.getCurrentStack().size() > 1) {
            pop();
        } else if (mFragNavController.getCurrentStack().size() == 1) {

            if (mFragNavController.getCurrentStackIndex() == INDEX_HOME) {

                builder.setTitle("Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                System.exit(0);

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                //mFragNavController.switchTab(INDEX_HOME);
                //bottomBar.selectTabAtPosition(INDEX_HOME);
                bottomBar.setSelectedItemId(R.id.tab_home);
                mFragNavController.switchTab(INDEX_HOME);
            }

        }

    }

    public void push(Fragment fragment) {
        mFragNavController.pushFragment(fragment);
    }

    public void pop() {
        mFragNavController.popFragment();
    }

    public void clearStack() {
        mFragNavController.clearStack();
    }

    public void switchStack(int index) {
        mFragNavController.switchTab(index);
    }


    public FragNavController getmFragNavController() {
        return mFragNavController;
    }

    public void setmFragNavController(FragNavController mFragNavController) {
        this.mFragNavController = mFragNavController;
    }

    @Override
    public void onTabTransaction(Fragment fragment, int i) {
        if (onFragmentTransactionListener != null) {
            onFragmentTransactionListener.onTabTransaction(fragment, i);
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (onFragmentTransactionListener != null) {
            onFragmentTransactionListener.onFragmentTransaction(fragment, transactionType);
        }
    }

}
