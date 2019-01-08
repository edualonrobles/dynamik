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
import com.edualonso.dynamik.model.Player;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Created by edu_g on 03/07/2017.
 */

/**
 * Esta clase sirve para mostrar cada uno de los jugadores asociados a un equipo.
 */

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.MyViewHolder>{

    private ArrayList<Player> mDataset;
    private Activity activity;
    private OnImageClickListener listener;
    private FirebaseStorage storage;

    public PlayerListAdapter(Activity context, ArrayList<Player> myDataset, OnImageClickListener listener) {
        mDataset = myDataset;
        this.activity=context;
        this.listener=listener;
        this.storage = FirebaseStorage.getInstance();
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        final Player mPlayer = mDataset.get(position);
        holder.mTextViewName.setText(mPlayer.getName());
        holder.mTextViewSurName.setText(mPlayer.getSurname());
        holder.mTextViewAge.setText(String.valueOf(mPlayer.getAge()));
        //holder.mImage.setImageResource(mPlayer.getPhoto());

        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("players").child(mPlayer.getTeam());
        final StorageReference spaceRef = imagesRef.child(mPlayer.getIdPlayer()+".jpg");
        spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).using(new FirebaseImageLoader()).load(spaceRef).into(holder.mImage);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               holder.mImage.setImageResource(R.drawable.ic_user);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.omImageClick(mPlayer);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextViewName;
        private TextView mTextViewSurName;
        private TextView mTextViewAge;
        private ImageView mImage;
        private MyViewHolder(View v) {
            super(v);
            mTextViewName = v.findViewById(R.id.person_name);
            mTextViewSurName =  v.findViewById(R.id.person_surname);
            mTextViewAge =  v.findViewById(R.id.person_age);
            mImage = v.findViewById(R.id.person_image);
        }
    }



    public interface OnImageClickListener{
        void omImageClick(Player p);
    }

}
