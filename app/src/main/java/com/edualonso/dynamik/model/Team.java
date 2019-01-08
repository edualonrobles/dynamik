package com.edualonso.dynamik.model;

import android.content.Intent;



import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


/**
 * Created by edu_g on 05/07/2017.
 */

/**
 * DataClass Team : Representa la información que debe contener el objeto Team
 */

public class Team implements Serializable{

    public static String TEAMNAME="teamName";


    private String idTeam,idComp,teamName,city,twitter,idCompetition,stadium;
    private int position,photo,members;

    //private List<String> users;         // Array que contiene los ids, de los usuarios que estan suscritos a este equipo
    //private List<String> persons;       // Array que contiene los ids de los jugadores de este equipo


    public Team(){

    }

    public Team(String idTeam){
        this.idTeam=idTeam;
    }

    public Team(String idTeam ,String idComp, String teamName, String city, int members, int position, int photo,String twitter,String idCompetition,String stadium){
        this.idTeam= idTeam;
        this.idComp = idComp;
        this.teamName= teamName;
        this.city=city;
        this.position=position;
        this.members=members;
        this.photo=photo;
        this.twitter=twitter;
        this.idCompetition = idCompetition;
        this.stadium = stadium;
        //this.users = teamUsers;
        //this.persons = teamPersons;
    }


    public String getIdTeam() {return idTeam;}
    public void setIdTeam(String idTeam) {this.idTeam = idTeam;}

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPhoto() {
        return photo;
    }
    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public int getMembers() {
        return members;
    }
    public void setMembers(int members) {this.members = members;}

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public String getTwitter() {return twitter;}
    public void setTwitter(String twitter) {this.twitter = twitter;}

    public String getIdCompetition() {return idCompetition;}
    public void setIdCompetition(String idCompetition) {this.idCompetition = idCompetition;}

    public String getStadium() {return stadium;}
    public void setStadium(String stadium) {this.stadium = stadium;}

    public String getIdComp() {return idComp;}
    public void setIdComp(String idComp) {this.idComp = idComp;}

    //    public List<String> getUsers() {return users;}
//    public void setUsers(List<String> users) {this.users = users;}
//
//    public List<String> getPersons() {return persons;}
//    public void setPersons(List<String> persons) {this.persons = persons;}

    //public RealmList<Player> getTeamMembers() {return teamMembers;}

    //public void setTeamMembers(RealmList<Player> teamMembers) {this.teamMembers = teamMembers;}

    //    public HashMap<Player, Integer> getTeamMembers() {return teamMembers;}
//
//    public void setTeamMembers(HashMap<Player, Integer> teamMembers) {this.teamMembers = teamMembers;}
}
