package com.edualonso.dynamik.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.Player;


/**
 * Created by edu_g on 03/07/2017.
 */

/**
 * DetailPersonActivity: Esta clase sirve para mostrar el detalle de un jugador.
 */

public class DetailPersonActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "extra_person";
    private Player mPlayer;
    private TextView tvName, tvGoals, tvAge;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        mPlayer = (Player) getIntent().getExtras().getSerializable(EXTRA_PERSON);

        tvName = (TextView) findViewById(R.id.person_name);
        tvAge = (TextView) findViewById(R.id.person_age);
        tvGoals = (TextView) findViewById(R.id.person_goals);
        ivImage = (ImageView) findViewById(R.id.person_image);

        tvName.setText(mPlayer.getName());
        tvAge.setText(String.valueOf(mPlayer.getAge()));
        tvGoals.setText(String.valueOf(mPlayer.getGoals()));
        ivImage.setImageResource(mPlayer.getPhoto());



    }
}