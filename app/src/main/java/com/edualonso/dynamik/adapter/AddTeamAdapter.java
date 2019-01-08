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
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.utils.Utils;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by edu_g on 05/07/2017.
 */

/**
 *  Esta clase sirve para mostrar cada uno de los equipos que se muestran cuando queremos suscribirnos a un equipo.
 */

public class AddTeamAdapter extends RecyclerView.Adapter<AddTeamAdapter.MyViewHolder> {


    private List<Team> mTeamData;
    private Activity activity;
    private AddTeamAdapter.OnImageClickListener listener;
    private FirebaseStorage storage ;

    public AddTeamAdapter(Activity context, List<Team> myTeamData, AddTeamAdapter.OnImageClickListener listener) {
        mTeamData = myTeamData;
        this.activity=context;
        this.listener=listener;
        storage = FirebaseStorage.getInstance();
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        final Team mTeam = mTeamData.get(position);
        holder.mTextViewName.setText(mTeam.getTeamName());
        holder.mTextViewCity.setText(mTeam.getCity());
        holder.mTextViewPos.setText(String.valueOf(mTeam.getPosition()));
        final StorageReference spaceRef = Utils.getTeamSpaceRef(storage,mTeam.getIdTeam()) ;
        //imagesRef.child(String.valueOf(mTeam.getPhoto())+".png");
        spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).using(new FirebaseImageLoader()).load(spaceRef).into(holder.mImage);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.omImageClick(mTeam);
                }
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTeamData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewName;
        public TextView mTextViewCity;
        public TextView mTextViewMembers;
        public TextView mTextViewPos;
        public ImageView mImage;

        public MyViewHolder(View v) {
            super(v);
            mTextViewName =(TextView)v.findViewById(R.id.team_name);
            mTextViewCity = (TextView)v.findViewById(R.id.team_city);
            mTextViewPos = (TextView)v.findViewById(R.id.team_position);
            mTextViewCity = (TextView)v.findViewById(R.id.team_city);
            mImage =(ImageView) v.findViewById(R.id.team_image);
        }
    }
    public interface OnImageClickListener{
        void omImageClick(Team t);
    }
}
