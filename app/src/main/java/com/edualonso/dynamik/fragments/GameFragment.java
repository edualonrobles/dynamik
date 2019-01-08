package com.edualonso.dynamik.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.adapter.GameListAdapter;
import com.edualonso.dynamik.model.Game;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 07/07/2017.
 */

/**
 * Esta clase sirve para representar los partidos asociados a cada uno de los equipos.
 */

public class GameFragment extends BaseFragment implements GameListAdapter.onGameClick{


    private MyModel model;
    private RecyclerView recycler;
    private List<Game> myGames;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    MyActivityListener listener;
    private Team team;
    private List<Game> myGamesList;

    public static GameFragment newInstance(Team t, MyActivityListener listener) {
        GameFragment gameFragment = new GameFragment();
        gameFragment.listener = listener;
        gameFragment.team = t;
        return gameFragment;
    }

    public static GameFragment newInstance(MyActivityListener listener){
        GameFragment gameFragment = new GameFragment();
        gameFragment.listener = listener;
        return gameFragment;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Partidos";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = MyModel.getInstace();
        myGames = model.getGames();

        if(team!=null) {
            myGamesList = new ArrayList<>();
            for (Game g : myGames) {
                if (g.getIdTeamL().equals(team.getIdTeam()) || g.getIdTeamV().equals(team.getIdTeam())) {
                    myGamesList.add(g);
                }
            }
            mAdapter = new GameListAdapter(getActivity(), myGamesList, this);
        }else {
            mAdapter = new GameListAdapter(getActivity(), myGames, this);
        }

        recycler = (RecyclerView) view.findViewById(R.id.game_list);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);


        recycler.setAdapter(mAdapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        return view;

    }
    @Override
    public void onGameClick(Game g) {
        listener.goGameDetail(g);
    }


    public interface MyActivityListener{
        void goGameDetail(Game g);
    }

}
