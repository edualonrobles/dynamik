package com.edualonso.dynamik.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.adapter.RankingAdapter;
import com.edualonso.dynamik.model.Game;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Ranking;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.Competition;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu_g on 31/07/2017.
 */

/**
 * Esta clase sirve para mostrar la clasificación asociada a la competición de cada uno de los equipos
 */

public class RankingFragment extends BaseFragment{

    private MyModel model;
    private List<Competition> competitions;
    private List<Game> games;
    private Ranking ranking;
    private List<Ranking> rankings;
    private List<Team> teams;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Competition comp;
    //private TextView mCompetitionName;

    @BindView(R.id.competition_name) TextView mCompetitionName;
    private int pts;


    public static RankingFragment newInstance(Competition competition){
        RankingFragment rankingFragment = new RankingFragment();
        rankingFragment.comp = competition;
        return rankingFragment;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Clasificación";
    }

    public RankingFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ranking,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = MyModel.getInstace();
        games = model.getGames();
        teams = model.getTeams();
        //competitions = model.getCompetitions();
        ButterKnife.bind(this,view);

        ranking = new Ranking();
        rankings = model.getRanking();
        //rankings = ranking.generateRanking(teams,comp);
        //rankings = ranking.ordenarRankings(rankings);

        //mCompetitionName = (TextView) view.findViewById(R.id.competition_name);

        mCompetitionName.setText(comp.getNameCompetition());
        recycler = (RecyclerView) view.findViewById(R.id.ranking_list);

        mAdapter = new RankingAdapter(getActivity(), rankings);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);


        recycler.setAdapter(mAdapter);
    }
}
