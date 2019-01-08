package com.edualonso.dynamik;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.Competition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edu Alonso on 03/11/2018.
 */
public class AddInfo {

    DatabaseReference dbTeam,dbPlayer,dbCompetition,dbGames,dbRanking;
    List emptyList,persons,persons2,persons3,games,teams;
    private FirebaseStorage storage ;

    public AddInfo(Context context) {
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        dbTeam = FirebaseDatabase.getInstance().getReference().child("Teams");
        dbPlayer = FirebaseDatabase.getInstance().getReference().child("Players");
        dbCompetition = FirebaseDatabase.getInstance().getReference().child("Competition");
        dbGames = FirebaseDatabase.getInstance().getReference().child("Games");
        dbRanking = FirebaseDatabase.getInstance().getReference().child("Ranking");
//        storage = FirebaseStorage.getInstance();

        /*-----------------Cargo de forma temporal los datos----------- */

        // ------------------------------- TEAMS & PLAYERS ---------------------------------

        emptyList = new ArrayList<String>(); //emptyList.add("KpKTyW7dAOpla39hCN6");
        games = new ArrayList<String>();
        teams = new ArrayList<Team>();
        persons = new ArrayList<String>();
        persons2 = new ArrayList<String>();
        persons3 = new ArrayList<String>();

        //Team value = Gson.fromJSON(context.getAssets().open("info/teams.json"), Team.class);
        // teams = Js
        //String myJson=inputStreamToString(context.getResources().openRawResource(R.raw.teams));
        //eam[] teams= new Gson().fromJson(myJson, Team[].class);

        Competition comp1 = new Competition("","","3º Division - Grupo VIII",games);
        String compKey = dbCompetition.push().getKey(); comp1.setIdCompetition(compKey); dbCompetition.child(compKey).setValue(comp1);

        /*for(int i=0; i<teams.length;i++){
            /*String teamKey = dbTeam.push().getKey();
            Team t = new Team(teamKey,compKey,teams[i].getTeamName(),"",0,0,0,"","","");
            dbTeam.child(teamKey).setValue(t);
            StorageReference teamImageRef = storageRef.child("logos");

        }*/
        //String key = dbTeam.push().getKey(); Team t = new Team(key,compKey,"Calasanz","Salamanca",0,1,R.drawable.calasanz_logo,"","","Calasanz Stadium");

    }
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public void uploadImage(StorageReference storageRef, Team team, Context context){
        //Uri fileUri = Uri.parse("android.resource://com.edualonso.dynamik/res/raw/teamImages/"+team.getTeamName());


            /*InputStream stream = context.getResources().openRawResource(R.raw.arandinacf);*/
            /*Uri path = Uri.parse("android.resource://" + context.getPackageName() +
                "/drawable/teamImages/" +team.getTeamName()+".jpg" );*/
            //Uri path = Uri.parse("drawable://" + "teamImages/" + team.getTeamName()+ ".jpg");
            Uri path = Uri.parse("drawable://" + "teamImages/" + team.getTeamName() + ".jpg");
            Uri file = Uri.fromFile(new File(path.toString())); //+team.getTeamName()+".jpg"

            team.setTeamName(team.getTeamName().replaceAll("\\s+",""));
            team.setTeamName(team.getTeamName().replaceAll("\\`+",""));
            team.setTeamName(team.getTeamName().replaceAll("\\´+",""));

            StorageReference riversRef = storageRef.child(team.getTeamName().trim());
            UploadTask uploadTask = riversRef.putFile(file);
            //UploadTask uploadTask = riversRef.putStream(stream);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d("","");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d("","");
                }
            });


        //InputStream stream = new FileInputStream(inputStreamToString(context.getResources().openRawResource(R.raw.teams)));
    }

}
