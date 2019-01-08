package com.edualonso.dynamik.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.Game;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.utils.Utils;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu_g on 25/07/2017.
 */

/**
 * Esta clase sirve para mostrar el detalle de cada partido asociado a un equipo.
 */

public class DetailGameFragment extends BaseFragment {

    private static MyModel model;
    private List<Game> games;
    private List<Team> teams;
    private static Game myGame;
    private Team team1,team2;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static FirebaseStorage storage;

    @BindView(R.id.team1_image) ImageView team1_iv;
    @BindView(R.id.team2_image) ImageView team2_iv;
    @BindView(R.id.team1_goals) TextView team1_goals;
    @BindView(R.id.team2_goals) TextView team2_goals;
    @BindView(R.id.team1_name) TextView team1_name;
    @BindView(R.id.team2_name) TextView team2_name;
    @BindView(R.id.initial_team1) RecyclerView initial_team1;
    @BindView(R.id.initial_team2) RecyclerView initial_team2;
    @BindView(R.id.bench_team1) RecyclerView bench_team1;
    @BindView(R.id.bench_team2) RecyclerView bench_team2;
    @BindView(R.id.coachs_team1) RecyclerView coach_team1;
    @BindView(R.id.coachs_team2) RecyclerView coach_team2;


    public static DetailGameFragment newInstance(Game g){
        model = MyModel.getInstace();
        DetailGameFragment f = new DetailGameFragment();
        myGame = g;
        storage = FirebaseStorage.getInstance();
        return f;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Detalle de Partido";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_detail,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        teams = model.getTeams();

        for(Team t : teams){
            if(t.getIdTeam().equals(myGame.getIdTeamL())){
                team1 = t;
            }else if(t.getIdTeam().equals(myGame.getIdTeamV())){
                team2 = t;
            }
        }

        team1_goals.setText(String.valueOf(myGame.getGoalsL()));
        team2_goals.setText(String.valueOf(myGame.getGoalsV()));
        team1_name.setText(team1.getTeamName());
        team2_name.setText(team2.getTeamName());
        mLayoutManager = new LinearLayoutManager(getActivity());

        final StorageReference spaceRef = Utils.getTeamSpaceRef(storage,team1.getIdTeam()) ;
        //imagesRef.child(String.valueOf(mTeam.getPhoto())+".png");
        spaceRef.getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).using(new FirebaseImageLoader()).load(spaceRef).into(team1_iv);
            }
        });
        final StorageReference spaceRef2 = Utils.getTeamSpaceRef(storage,team2.getIdTeam()) ;
        spaceRef2.getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).using(new FirebaseImageLoader()).load(spaceRef2).into(team2_iv);
            }
        });

//        initial_team1.setLayoutManager(mLayoutManager);
//        initial_team2.setLayoutManager(mLayoutManager);
//        bench_team1.setLayoutManager(mLayoutManager);
//        bench_team2.setLayoutManager(mLayoutManager);
//        coach_team1.setLayoutManager(mLayoutManager);
//        coach_team2.setLayoutManager(mLayoutManager);

        //mAdapter = new AddTeamAdapter(getActivity(),myGame.getInitialPlayersL(), this);
        //initial_team1.setAdapter(mAdapter);


        //initial_team1.setAdapter();


    }
}
