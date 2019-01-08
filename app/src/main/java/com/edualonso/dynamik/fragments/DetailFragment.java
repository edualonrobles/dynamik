package com.edualonso.dynamik.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import androidx.annotation.NonNull;


/**
 * Created by edu_g on 04/07/2017.
 */

/**
 * Esta clase sirve para mostrar el detalle de cada miembro del equipo.
 */

public class DetailFragment extends BaseFragment{
    public static final String EXTRA_PERSON = "extra_person";
    private Player mPlayer;
    private TextView tvName, tvGoals, tvAge;
    private ImageView ivImage;
    private FirebaseStorage storage ;
    private Activity activity;

    public static DetailFragment newInstance(Player p, Activity activity){
        DetailFragment f = new DetailFragment();
        f.mPlayer = p;
        f.activity = activity;
        f.storage = FirebaseStorage.getInstance();
        return f;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Detalle de Miembro";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mPlayer = (Player) getIntent().getExtras().getSerializable(EXTRA_PERSON);

        tvName = (TextView) view.findViewById(R.id.person_name);
        tvAge = (TextView) view.findViewById(R.id.person_age);
        tvGoals = (TextView) view.findViewById(R.id.person_goals);
        ivImage = (ImageView) view.findViewById(R.id.person_image);


        tvName.setText(mPlayer.getName());
        tvAge.setText(String.valueOf(mPlayer.getAge()));
        tvGoals.setText(String.valueOf(mPlayer.getGoals()));
        //ivImage.setImageResource(mPlayer.getPhoto());

        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("players").child(mPlayer.getTeam());
        final StorageReference spaceRef = imagesRef.child(mPlayer.getIdPlayer()+".jpg");
        spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).using(new FirebaseImageLoader()).load(spaceRef).into(ivImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                ivImage.setImageResource(R.drawable.ic_user);
            }
        });
    }

}
