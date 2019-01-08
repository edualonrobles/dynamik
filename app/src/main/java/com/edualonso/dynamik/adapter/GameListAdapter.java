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
import com.edualonso.dynamik.model.Game;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by edu_g on 21/07/2017.
 */


/**
 * Esta clase sirve para mostrar cada uno de los partidos asociados a un equipo.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder>{


    private List<Game> mDataset;
    private Activity activity;

    private MyModel model;
    private List<Team> teams;
    private Team team1,team2;
    private onGameClick listener;
    private FirebaseStorage storage ;




    public GameListAdapter(Activity context, List<Game> myDataset, onGameClick listener) {
        mDataset = myDataset;
        this.activity=context;
        this.listener = listener;
        model = MyModel.getInstace();
        teams = model.getTeams();
        storage = FirebaseStorage.getInstance();
    }


    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new GameViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GameViewHolder holder, int position) {
        final Game game = mDataset.get(position);

//        for(Game games : mDataset){
            for(Team team : teams) {
                if (game.getIdTeamL().equals(team.getIdTeam())) {
                    team1 = team;
                }else if(game.getIdTeamV().equals(team.getIdTeam())){
                    team2 = team;
                }
            }
//        }

        holder.tvTeamName1.setText(team1.getTeamName());
        holder.tv_Team1Goal.setText(String.valueOf(game.getGoalsL()));
        holder.tv_Team2Goal.setText(String.valueOf(game.getGoalsV()));
        holder.tvTeamName2.setText(team2.getTeamName());

        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("logos");
        final StorageReference spaceRef = imagesRef.child(String.valueOf(team1.getIdTeam())+".png");
        spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).using(new FirebaseImageLoader()).load(spaceRef).into(holder.ivTeam1);
            }
        });
        final StorageReference spaceRef2 = imagesRef.child(String.valueOf(team2.getIdTeam())+".png");
        spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).using(new FirebaseImageLoader()).load(spaceRef2).into(holder.ivTeam2);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onGameClick(game);
                }
            }
        });

        
    }



    public static class GameViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tvTeamName1,tvTeamName2,tv_Team1Goal,tv_Team2Goal;
        private ImageView ivTeam1,ivTeam2;

        public GameViewHolder(View v) {
            super(v);
            ivTeam1 = v.findViewById(R.id.team1_image);
            ivTeam2 = v.findViewById(R.id.team2_image);
            tvTeamName1 = v.findViewById(R.id.tv_teamname1);
            tvTeamName2 = v.findViewById(R.id.tv_teamname2);
            tv_Team1Goal = v.findViewById(R.id.tv_team1goals);
            tv_Team2Goal = v.findViewById(R.id.tv_team2goals);


        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface onGameClick{
        void onGameClick(Game g);
    }

}
