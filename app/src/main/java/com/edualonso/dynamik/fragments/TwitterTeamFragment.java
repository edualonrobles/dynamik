package com.edualonso.dynamik.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.ViewTitleOwner;
import com.edualonso.dynamik.model.Team;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by edu_g on 20/07/2017.
 */

/**
 * Esta clase sirve para mostrar el twitter asociado a cada equipo.
 */

public class TwitterTeamFragment extends ListFragment implements ViewTitleOwner {

    private static TwitterTeamFragment twitter;
    Team team;

    public static TwitterTeamFragment newInstance(Team t){
        twitter = new TwitterTeamFragment();
        twitter.team = t;
        return twitter;
    }

    @Override
    public void setViewTitle(@NotNull String title) {
        //NO OP
    }

    @Nullable
    @Override
    public String getViewTitle() {
        return "Twitter";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TwitterAuthConfig authConfig = new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET");
//        TwitterTeamFragment.with(this, new Twitter(authConfig));


        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(team.getTwitter())
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_twitter_timeline, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}




