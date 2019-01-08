package com.edualonso.dynamik.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.User;
import com.edualonso.dynamik.utils.Utils;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by edu_g on 06/07/2017.
 */

/**
 * Esta clase representa la funcionalidad de ver el detalle de cada uno de los equipos.
 */

public class DetailTeamFragment extends BaseFragment{


    public static final String EXTRA_TEAM = "extra_person";
    private Team mTeam;
    private TextView tvName, tvCity, tvMembers, tvPos;
    private ImageView ivImage;
    private Button myPetButton,delTeamButton;
    private MyModel myModel;
    private List<User> users;
    private List<Team> teams;
    private boolean singToaTeam;
    private User myUser;
    private static FirebaseStorage storage;
    DatabaseReference dbTeam,dbUser;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    AlertDialog.Builder builder;


    public static DetailTeamFragment newInstance(Team t){

        DetailTeamFragment f = new DetailTeamFragment();
        storage = FirebaseStorage.getInstance();
        f.mTeam = t;
        return f;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return mTeam != null ? mTeam.getTeamName() : "Detalle de equipo";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mPerson = (Player) getIntent().getExtras().getSerializable(EXTRA_PERSON);

        prefs = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor= prefs.edit();

        dbUser = Utils.getDatabaseReference("Users");

        myModel = MyModel.getInstace();
        users = myModel.getUsers();
        teams = myModel.getTeams();

        tvName = (TextView) view.findViewById(R.id.team_name_det);
        tvCity = (TextView) view.findViewById(R.id.team_city_det);
        tvMembers = (TextView) view.findViewById(R.id.team_members_det);
        tvPos = (TextView) view.findViewById(R.id.team_position_det);
        ivImage = (ImageView) view.findViewById(R.id.team_image_det);
        myPetButton = (Button) view.findViewById(R.id.send_pet);
        delTeamButton = (Button) view.findViewById(R.id.del_team);
        delTeamButton.setVisibility(View.INVISIBLE);

        //------------------- Shared Preferences ------------------

        singToaTeam=true;

        //----------------------- Dialog -------------------------

        builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);

        //------------------------ CODE -------------------------

        for(User u : users){
            if(u.getId().equals(prefs.getString("id",""))){         //FindUser ...
                myUser = u;
                for(String idTeam: myUser.getTeams()){
                    if(mTeam.getIdTeam().equals(idTeam)){
//                        myPetButton.setText("Petición enviada");    // TODO eliminarEquipo
//                        myPetButton.setEnabled(false);
                        singToaTeam = false;

                        myPetButton.setText("Subscrito");
                        myPetButton.setVisibility(View.INVISIBLE);
                        delTeamButton.setVisibility(View.VISIBLE);

                    }
                }
            }
        }


        myPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //System.out.println(users);

                //System.out.println(users.get(0).getId());
                if(singToaTeam){
                    myUser.getTeams().add(mTeam.getIdTeam());
                    dbUser.child(myUser.getId()).child("teams").setValue(myUser.getTeams());
                    myPetButton.setText("Eliminar de mis equipos");
                    myPetButton.setEnabled(false);
                    delTeamButton.setVisibility(View.VISIBLE);
                    myPetButton.setVisibility(View.INVISIBLE);

                }
                else{
                    for(int i=0;i<myUser.getTeams().size();i++) {
                        if(mTeam.getIdTeam().equals(myUser.getTeams().get(i)))
                            myUser.getTeams().remove(i);
                    }
                    myPetButton.setText("Enviar Petición");
                    singToaTeam = true;
                    myPetButton.setEnabled(true);
                    myPetButton.setVisibility(View.VISIBLE);
                    delTeamButton.setVisibility(View.INVISIBLE);

                }
//                System.out.println(mTeam.getUsers());

            }
        });
        delTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("Delete team")
                        .setMessage("Are you sure you want to delete this team to your feed?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                for(int i=0;i<myUser.getTeams().size();i++) {
                                    if(mTeam.getIdTeam().equals(myUser.getTeams().get(i)))
                                        myUser.getTeams().remove(i);
                                        dbUser.child(myUser.getId()).child("teams").setValue(myUser.getTeams());
                                }
                                myPetButton.setText("Enviar Petición");
                                singToaTeam = true;
                                myPetButton.setEnabled(true);
                                myPetButton.setVisibility(View.VISIBLE);
                                delTeamButton.setVisibility(View.INVISIBLE);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

        tvName.setText(mTeam.getTeamName());
        tvCity.setText(mTeam.getCity());
        tvMembers.setText(String.valueOf(mTeam.getMembers()));
        tvPos.setText(String.valueOf(mTeam.getPosition()));
        //ivImage.setImageResource(mTeam.getPhoto());

        final StorageReference storageReference = Utils.getTeamSpaceRef(storage,mTeam.getIdTeam());
        storageReference.getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).using(new FirebaseImageLoader()).load(storageReference).into(ivImage);
            }
        });
    }
}
