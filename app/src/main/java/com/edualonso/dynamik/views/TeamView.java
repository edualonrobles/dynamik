package com.edualonso.dynamik.views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edualonso.dynamik.R;
import com.edualonso.dynamik.fragments.MyTeamsFragment;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.Competition;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


/**
 * Created by edu_g on 19/07/2017.
 */

/**
 *  Esta clase se utiliza para crear por código la vista de cada equipo.
 */

public class TeamView extends RelativeLayout {

    private TextView teamName;
    View gameButton, membersButton, rankingButton, photosButton, twitterButton;
    ImageView imageTeam;
    private MyModel model;
    private Team myTeam;
    private Competition competition;
    private List<Competition> competitions;
    private FirebaseStorage storage;
    private long mLastClickTime = 0;


    private MyTeamsFragment.MyActivityListener listener;

    public TeamView(Context context) {
        this(context, null);
    }

    public TeamView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeamView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.fragment_basic_team, this);
        initview();
    }

    private void initview() {

        storage = FirebaseStorage.getInstance();
        teamName = findViewById(R.id.tvTeamName);
        gameButton = findViewById(R.id.cardMatches);
        membersButton = findViewById(R.id.cardViewMembers);
        rankingButton = findViewById(R.id.cardViewRanking);
        photosButton = findViewById(R.id.cardViewPhotos);
        twitterButton = findViewById(R.id.cardViewTwitter);
        imageTeam = findViewById(R.id.iv_teamphoto);


    }


    public void setTeam(final Team team, Activity activity, MyTeamsFragment.MyActivityListener teamListener) {

        listener = teamListener;
        model = MyModel.getInstace();
        competitions = model.getCompetitions();

        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("logos");
        final StorageReference spaceRef = imagesRef.child(String.valueOf(team.getIdTeam()) + ".png");

        for (Competition comp : competitions) {
            if (comp.getIdCompetition().equals(team.getIdComp())) {
                competition = comp;
            }
        }


        teamName.setText(team.getTeamName());
        spaceRef.getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).using(new FirebaseImageLoader()).load(spaceRef).into(imageTeam);

                //Glide.with(getActivity()).load(spaceRef).into(userImage);
            }
        });
        gameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (listener != null) {
                    listener.onClickGames(team);
                }
            }
        });
        membersButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (listener != null) {
                    listener.onClickMembers(team);
                }
            }
        });
        photosButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (listener != null) {
                        listener.onClickPhotos(team);
                    }
                }
            }
        );

        rankingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (listener != null) {
                    listener.onClickRanking(competition);
                }
            }
        });

        twitterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (!team.getTwitter().equals("")) {
                    if (listener != null) {
                        listener.onClickTwitter(team);
                    }
                } else {

                }
            }
        });

    }

}
