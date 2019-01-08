package com.edualonso.dynamik.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.Competition;
import com.edualonso.dynamik.model.user.User;
import com.edualonso.dynamik.views.TeamView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by edu_g on 19/07/2017.
 */

/**
 * Esta clase sirve para mostrar el rootFragment "Teams".
 */

public class MyTeamsFragment extends BaseFragment {


    private ViewPager pager;
    private TabLayout tabLayout;
    private MyModel myModel;
    private List<User> users;
    private List<Team> teams;
    static private List<Team> myUserTeams;
    private User myUser;
    private List<View> teamViews;
    private MyTeamsFragment.MyActivityListener listener;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public static MyTeamsFragment newInstance(MyTeamsFragment.MyActivityListener listener) {
        MyTeamsFragment myTeamsFragment = new MyTeamsFragment();
        if(listener!=null) {
            myTeamsFragment.listener = listener;
        }
        Bundle args = new Bundle();
        myTeamsFragment.setArguments(args);
        return myTeamsFragment;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Mis equipos";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();


        myModel = MyModel.getInstace();
        users = myModel.getUsers();
        teams = myModel.getTeams();
        teamViews= new ArrayList<>();
        myUserTeams = new ArrayList<Team>();

        tabLayout = (TabLayout) view.findViewById(R.id.tl_competitions);
        pager = (ViewPager) view.findViewById(R.id.pager_teams);

        for (User u : users) {
            if (u.getId().equals(prefs.getString("id", ""))) {
                myUser = u;
            }
        }
        if(myUser.getTeams().size()>0){


            for (int i = 0; i < myUser.getTeams().size(); i++) {
                for (Team t : teams) {
                    if (t.getIdTeam().equals(myUser.getTeams().get(i))) {
                        myUserTeams.add(t);
                        TeamView teamView = new TeamView(getActivity());
                        teamView.setTeam(t,getActivity(),listener);
                        teamViews.add(teamView);
                    }
                }
            }
        }



        pager.setAdapter(new MyTeamPagerAdapter(getActivity()));
        for (Team t : teams) {
            tabLayout.addTab(tabLayout.newTab().setText(t.getTeamName()));
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(pager);


//        tabLayout.addTab(tabLayout.newTab().setText(R.string.competition));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.matches));

        //Creo un tabLayout por equipo al que estoy suscrito


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                tabLayout.getTabAt(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myteams, container, false);
        return view;

    }


    class MyTeamPagerAdapter extends PagerAdapter {
        //private static int NUM_ITEMS = 2;
        Context context;
        int numTabs;


        public MyTeamPagerAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return teamViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View item = teamViews.get(position);
            container.addView(item);
            return item;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return myUserTeams.get(position).getTeamName();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    public interface MyActivityListener{
        void onClickGames(Team t);
        void onClickMembers(Team t);
        void onClickPhotos(Team t);
        void onClickRanking(Competition comp);
        void onClickTwitter(Team t);
    }

}
