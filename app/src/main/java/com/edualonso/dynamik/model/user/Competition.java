package com.edualonso.dynamik.model.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 21/07/2017.
 */

/**
 * DataClass Competition : Representa la información que debe contener el objeto Competition
 */


public class Competition {
    String idCompetition,idRanking,nameCompetition;
    List<String> games;

    public Competition(){
        games = new ArrayList<>();
    }

    public Competition(String idCompetition, String idRanking, String nameCompetition,List<String> games){
        this.idCompetition = idCompetition;
        this.idRanking = idRanking;
        this.nameCompetition = nameCompetition;
        this.games = games;
    }

    public String getIdCompetition() {return idCompetition;}
    public void setIdCompetition(String idCompetition) {this.idCompetition = idCompetition;}

    public String getIdRanking() {return idRanking;}
    public void setIdRanking(String idRanking) {this.idRanking = idRanking;}

    public String getNameCompetition(){return nameCompetition;}
    public void setNameCompetition(String nameCompetition) {this.nameCompetition = nameCompetition;}

    public List<String> getGames() {return games;}
    public void setGames(List<String> games) {this.games = games;}
}
