package com.edualonso.dynamik;

import android.content.Context;
import android.content.SharedPreferences;


import com.edualonso.dynamik.model.Game;
import com.edualonso.dynamik.model.Player;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.Competition;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.edualonso.dynamik.R.*;



/**
 * Created by edu_g on 21/07/2017.
 */

public class CreateTestingData {


    DatabaseReference dbTeam,dbPlayer,dbCompetition,dbGames,dbRanking;
    List emptyList,persons,persons2,persons3,games;
    private FirebaseStorage storage ;

    public CreateTestingData(){
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
        persons = new ArrayList<String>();
        persons2 = new ArrayList<String>();
        persons3 = new ArrayList<String>();

        Competition comp1 = new Competition("","","Liga Fantastica",games);
        String compKey = dbCompetition.push().getKey(); comp1.setIdCompetition(compKey); dbCompetition.child(compKey).setValue(comp1);

        String key = dbTeam.push().getKey(); Team t = new Team(key,compKey,"Calasanz","Salamanca",0,1,R.drawable.calasanz_logo,"","","Calasanz Stadium");
        String key2 = dbTeam.push().getKey(); Team t2 = new Team(key2,compKey,"EE.UU team", "Texas",0,2,R.drawable.eeuu_logo,"USASoccer","","EEUU stadium");
        String key3 = dbTeam.push().getKey(); Team t3 = new Team(key3,compKey,"Real Madrid","Madrid ",0,3,R.drawable.real_madrid_logo,"realmadrid","","Santiago Bernabeu");

        dbTeam.child(key).setValue(t); dbTeam.child(key).child("persons").setValue(persons);
        dbTeam.child(key2).setValue(t2); dbTeam.child(key2).child("persons").setValue(persons2);
        dbTeam.child(key3).setValue(t3); dbTeam.child(key3).child("persons").setValue(persons3);

        Player p1 = new Player("","Edu","Alonso",21,R.drawable.ic_myuserphto,10,key);
        Player p2 = new Player("","Rodrigo","Garcia",9,R.drawable.ic_myuserphto,39,key);
        Player p3 = new Player("","David","Guinaldo",10,R.drawable.ic_myuserphto,0,key);
        Player p4 = new Player("","Donald","Trump",60,R.drawable.ic_myuserphto,15,key2);
        Player p5 = new Player("","Zinedine","Zidane",39,R.drawable.ic_myuserphto,18,key3);

        String key4 = dbPlayer.push().getKey();   p1.setIdPlayer(key4); dbPlayer.child(key4).setValue(p1);
        String key5 = dbPlayer.push().getKey();   p2.setIdPlayer(key5); dbPlayer.child(key5).setValue(p2);
        String key6 = dbPlayer.push().getKey();   p3.setIdPlayer(key6); dbPlayer.child(key6).setValue(p3);
        String key7 = dbPlayer.push().getKey();   p4.setIdPlayer(key7); dbPlayer.child(key7).setValue(p4);
        String key8 = dbPlayer.push().getKey();   p5.setIdPlayer(key8); dbPlayer.child(key8).setValue(p5);

        //-----------------------------COMPETITION & GAMES -----------------------------------------


        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(date);

        Calendar calendar;
        Game game1 = new Game("",t.getIdTeam(),t2.getIdTeam(),t.getStadium(),"",0,0,"");
        Game game2 = new Game("",t2.getIdTeam(),t3.getIdTeam(),t2.getStadium(),"",0,0,"");

        String game1Key = dbGames.push().getKey(); game1.setIdGame(game1Key); dbGames.child(game1Key).setValue(game1); dbGames.child(game1Key).child("date").setValue(stringdate); games.add(game1.getIdGame());
        String game2Key = dbGames.push().getKey(); game2.setIdGame(game2Key); dbGames.child(game2Key).setValue(game2); dbGames.child(game2Key).child("date").setValue(stringdate); games.add(game2.getIdGame());




        //-------------------------------- RANKING --------------------------------------




    }
}
