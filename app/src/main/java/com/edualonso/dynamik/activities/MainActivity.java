package com.edualonso.dynamik.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.edualonso.dynamik.NavController;
import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.user.User;
import com.ncapdevi.fragnav.FragNavController;

import org.jetbrains.annotations.Nullable;

import java.util.Stack;

/**
 * MainActivity: Esta clase recoge la funcionalidad principal de la aplicación
 */
public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener, ViewTitleOwner, FragNavController.TransactionListener {


    private BottomNavigationView bottomBar;
    private Fragment fragment;
    private User u;
    private int myPosition;
    private Stack<Fragment> stack;
    public static SharedPreferences prefs;
    private Activity activity;
    private MyModel myModel;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        /*No transaction callback for root Fragment, so force*/
        setViewTitle("Inicio");

        prefs = this.getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        myModel = MyModel.getInstace();

        u = new User();
        for (User user : myModel.getUsers()) {
            if (user.getId().equals(prefs.getString("id", ""))) {
                u = user;
            }
        }

        bottomBar = (BottomNavigationView) findViewById(R.id.bottomBar);
        NavController.init(getSupportFragmentManager(), activity, bottomBar,this.activity);
        NavController.getInstance().setOnFragmentTransactionListener(this);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int tabId = menuItem.getItemId();
                switch (tabId) {
                    case R.id.tab_home:
                        NavController.getInstance().switchStack(NavController.INDEX_HOME);
                        break;
                    case R.id.tab_team:
                        if (u.getTeams().size() <= 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("Debes de estar suscrito a un equipo para acceder a esta sección!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return false;
                        } else {
                            NavController.getInstance().switchStack(NavController.INDEX_TEAM);
                            break;
                        }

                    case R.id.tab_profile:
                        NavController.getInstance().switchStack(NavController.INDEX_PROFILE);

                }
                return true;
            }
        });
        bottomBar.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                NavController.getInstance().clearStack();
            }
        });

        /*bottomBar.setTabSelectionInterceptor(new TabSelectionInterceptor() {
            @Override
            public boolean shouldInterceptTabSelection(@IdRes int oldTabId, @IdRes int newTabId) {
                if (newTabId == R.id.tab_team) {
                    if (u.getTeams().size() <= 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Debes de estar suscrito a un equipo para acceder a esta sección!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                    }

                }
                return false;
            }
        });*/

    }


    @Override
    public Fragment getRootFragment(int i) {
        return NavController.getInstance().getRootFragment(i);
    }

    @Override
    public void onBackPressed() {
        NavController.getInstance().onBackPressed();
    }

    @Nullable
    @Override
    public String getViewTitle() {
        if (getSupportActionBar() != null && getSupportActionBar().getTitle() != null) {
            return getSupportActionBar().getTitle().toString();
        }
        return null;
    }

    @Override
    public void setViewTitle(@Nullable String s) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(s);
        }
    }

    @Override
    public void onTabTransaction(@android.support.annotation.Nullable Fragment fragment, int i) {
        if (fragment instanceof ViewTitleOwner) {
            setViewTitle(((ViewTitleOwner) fragment).getViewTitle());
        } else {
            setViewTitle(null);
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (fragment instanceof ViewTitleOwner) {
            setViewTitle(((ViewTitleOwner) fragment).getViewTitle());
        } else {
            setViewTitle(null);
        }
    }
}
