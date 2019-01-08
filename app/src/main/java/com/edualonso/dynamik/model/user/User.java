package com.edualonso.dynamik.model.user;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by edu_g on 07/07/2017.
 */

//Class User

/**
 * DataClass User : Representa la información que debe contener el objeto User
 */

public class User implements Serializable{

    public static final String USERNAME = "name";
    public static final  String PASSWORD = "pass";
    //RealM
    //Realm realm = null;

    //@PrimaryKey
    //String idUser;
    private String name,email;
    private String pass,id;
    private int photo;
    private List<String> teams;


    //private RealmList<Team> myTeams;

    public User(String id, String name, String email, String pass, int photo,List<String> myTeams ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.photo = photo;
        this.teams = myTeams;
    }

    public User(){
        teams = new ArrayList<>();
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPhoto() {return photo;}

    public void setPhoto(int photo) {this.photo = photo;}

    public List<String> getTeams() {return teams;}

    public void setTeams(List<String> teams) {this.teams = teams;}

    //public RealmList<Team> getMyTeams() {return myTeams;}

    //public void setMyTeams(RealmList<Team> myTeams) {this.myTeams = myTeams;}

    //    realm.executeTransaction(new Realm.Transaction(){
//        @Override
//        public void execute(Realm realm){
//            //realm.insertOrUpdate(user););
//        }
//    });


}
