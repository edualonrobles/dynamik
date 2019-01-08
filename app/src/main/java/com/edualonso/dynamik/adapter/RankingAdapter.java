package com.edualonso.dynamik.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Ranking;
import com.edualonso.dynamik.model.Team;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 31/07/2017.
 */

/**
 * Esta clase sirve para mostrar cada uno de los equipos que aparecen en un ranking.
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {

    private MyModel model;
    private List<Ranking> rankings;
    private Activity activity;
    private FirebaseStorage storage ;


    public RankingAdapter(Activity context,List<Ranking> myDataset) {
        rankings = myDataset;
        this.activity=context;
        storage = FirebaseStorage.getInstance();
        this.model = MyModel.getInstace();
    //    this.listener=listener;
    }


    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ranking, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final RankingAdapter.MyViewHolder holder, int position) {

        final Ranking ranking = rankings.get(position);
        //holder.mImage.setImageResource(ranking.getTeam().getPhoto());
        holder.mTeamName.setText(ranking.getName());
        holder.mTeamPosition.setText(String.valueOf(ranking.getPosition()));
        holder.mTeamGamesPlayed.setText(String.valueOf(ranking.getGamesPlayed()));
        holder.mTeamPts.setText(String.valueOf(ranking.getPoints()));
        holder.mTeamWins.setText(String.valueOf(ranking.getWins()));
        holder.mTeamDraws.setText(String.valueOf(ranking.getDraws()));
        holder.mTeamLoses.setText(String.valueOf(ranking.getLoses()));
        holder.mTeamGoalsF.setText(String.valueOf(ranking.getGoalsF()));
        holder.mTeamGoalsA.setText(String.valueOf(ranking.getGoalsA()));
        //holder.mTeamDiff.setText(String.valueOf(ranking.getDifferenceFA()));

        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("logos");
        if(!getTeamId(ranking.getName()).equals("")){
            final StorageReference spaceRef = imagesRef.child(getTeamId(ranking.getName())+".png");
            spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(activity).using(new FirebaseImageLoader()).load(spaceRef).into(holder.mImage);
                }
            });
        }else{
            holder.mImage.setImageResource(R.drawable.ic_user);
        }


    }

    public String getTeamId(String teamName){
        for(Team team : model.getTeams()){
            if(team.getTeamName().equals(teamName)){
                return team.getIdTeam();
            }
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return rankings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mCompetitionName,mTeamName,mTeamPosition,mTeamGamesPlayed,mTeamPts,mTeamWins,mTeamDraws,mTeamLoses,mTeamGoalsF,mTeamGoalsA,mTeamDiff;
        public ImageView mImage;
        public MyViewHolder(View v) {
            super(v);

            mImage =(ImageView) v.findViewById(R.id.ranking_team_image);
            mTeamName =(TextView)v.findViewById(R.id.tv_teamName_ranking);
            mTeamPosition = (TextView) v.findViewById(R.id.tv_position);
            mTeamGamesPlayed = (TextView) v.findViewById(R.id.tv_gamesPlayed);
            mTeamPts = (TextView)v.findViewById(R.id.tv_points);
            mTeamWins = (TextView)v.findViewById(R.id.tv_wins);
            mTeamDraws =(TextView)v.findViewById(R.id.tv_draws);
            mTeamLoses = (TextView)v.findViewById(R.id.tv_loses);
            mTeamGoalsF = (TextView)v.findViewById(R.id.tv_goalsF);
            mTeamGoalsA = (TextView)v.findViewById(R.id.tv_goalsA);
            //mTeamDiff = (TextView)v.findViewById(R.id.tv_diffFA);

        }
    }

}
