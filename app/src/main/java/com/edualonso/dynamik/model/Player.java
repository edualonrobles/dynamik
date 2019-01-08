package com.edualonso.dynamik.model;

import android.media.Image;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Created by edu_g on 03/07/2017.
 */

/**
 * DataClass Player : Representa la información que debe contener el objeto Player
 */

public class Player implements Serializable{

    //@PrimaryKey
    //private int idPerson;

    private String idPlayer,name,surname,team;
    private int age,photo,goals;

    public Player(){}

    public Player(String idPlayer, String name, String surname , int age , int photo, int goals, String team){

        this.idPlayer = idPlayer;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.team = team;
        this.photo=photo;
        this.goals=goals;
    }

    public String getIdPlayer() {return idPlayer;}
    public void setIdPlayer(String idPlayer) {this.idPlayer = idPlayer;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public int getPhoto() {return photo;}
    public void setPhoto(int photo) {this.photo = photo;}

    public int getGoals() {return goals;}
    public void setGoals(int goals) {this.goals = goals;}

    public String getTeam() {return team;}
    public void setTeam(String team) {this.team = team;}
}
