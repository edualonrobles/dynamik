package com.edualonso.dynamik.model;

import android.util.Log;


import com.edualonso.dynamik.model.user.Competition;
import com.edualonso.dynamik.model.user.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 14/07/2017.
 */

/**
 * DataClass MyModel : Clase abstracta que representa el modelo de la aplicación
 */

public class MyModel {


    private List<User>users = new ArrayList<User>();
    private List<Team>teams = new ArrayList<Team>();
    private List<Player> players = new ArrayList<Player>();
    private List<Game> games = new ArrayList<Game>();
    private List<Competition> competitions = new ArrayList<Competition>();
    private List<Ranking> ranking = new ArrayList<Ranking>();

    private static MyModel myModel;

    public static MyModel getInstace(){

        if(myModel==null){
            myModel = new MyModel();
        }
        return myModel;
    }

    private MyModel(){


        /* ------------MyModel --------------
                Clase Singleton, en la que cargaremos los arrays locales, para acceder a los datos en local
         */


        //-------------USER------------------------------

        DatabaseReference dbUser =
                FirebaseDatabase.getInstance().getReference()
                        .child("Users");
        dbUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                users.add(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("User Debug Change","User changes");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //--------------------Teams------------------

        DatabaseReference dbTeam =
                FirebaseDatabase.getInstance().getReference()
                        .child("Teams");
        dbTeam.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                teams.add(dataSnapshot.getValue(Team.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("Team Debug Change","Team changes");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            //----------------Team--------------------------
        DatabaseReference dbPerson =
                FirebaseDatabase.getInstance().getReference()
                        .child("Players");
        dbPerson.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                players.add(dataSnapshot.getValue(Player.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("Player Debug Change","Player changes");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------------------- Games ---------------------------

        DatabaseReference dbGames =
                FirebaseDatabase.getInstance().getReference()
                        .child("Games");
        dbGames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                games.add(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------------------------ Competitions ------------------------

        DatabaseReference dbComp =
                FirebaseDatabase.getInstance().getReference()
                        .child("Competition");
        dbComp.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                competitions.add(dataSnapshot.getValue(Competition.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference dbRanking =
                FirebaseDatabase.getInstance().getReference()
                        .child("Ranking");
        dbRanking.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ranking.add(dataSnapshot.getValue(Ranking.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public User findUser(String key){
        for(User u : users){
            if(key.equals(u.getId())){
                return u;
            }else{
                return null;
            }
        }
        return null;
    }

    public Team findTeam(String key){
        for(Team t : teams){
            if(key.equals(t.getIdTeam())){
                return t;
            }else{
                return null;
            }
        }
        return null;
    }

    public Player findPerson(String key){
        for(Player p : players){
            if(key.equals(p.getIdPlayer())){
                return p;
            }else{
                return null;
            }
        }
        return null;
    }


    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Game> getGames() {
        return games;
    }
    public void setGames(List<Game> games) {this.games = games;}

    public List<Competition> getCompetitions() {return competitions;}
    public void setCompetitions(List<Competition> competitions) {this.competitions = competitions;}

    public List<Ranking> getRanking() {
        return ranking;
    }

    public void setRanking(List<Ranking> ranking) {
        this.ranking = ranking;
    }
}

