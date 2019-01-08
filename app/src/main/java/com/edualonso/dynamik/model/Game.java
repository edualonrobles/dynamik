package com.edualonso.dynamik.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by edu_g on 21/07/2017.
 */

/**
 * DataClass Game : Representa la información que debe contener el objeto Game
 */


public class Game {

    String idGame,idTeamL,idTeamV,campo,idPlayerOfGame;
    int goalsL,goalsV;
    String date;
    //Calendar hour;

    public Game(){

    }

    public Game(String idGame, String idTeamL, String idTeamV, String campo, String idPlayerOfGame, int goalsL, int goalsV, String date) { // Calendar hour
        this.idGame = idGame;
        this.idTeamL = idTeamL;
        this.idTeamV = idTeamV;
        this.campo = campo;
        this.idPlayerOfGame = idPlayerOfGame;
        this.goalsL = goalsL;
        this.goalsV = goalsV;
        this.date = date;
        //this.hour = hour;
    }

    public String getIdGame() {return idGame;}
    public void setIdGame(String idGame) {this.idGame = idGame;}

    public String getIdTeamL() {return idTeamL;}
    public void setIdTeamL(String idTeamL) {this.idTeamL = idTeamL;}

    public String getIdTeamV() {return idTeamV;}
    public void setIdTeamV(String idTeamV) {this.idTeamV = idTeamV;}

    public String getCampo() {return campo;}
    public void setCampo(String campo) {this.campo = campo;}

    public String getIdPlayerOfGame() {return idPlayerOfGame;}
    public void setIdPlayerOfGame(String idPlayerOfGame) {this.idPlayerOfGame = idPlayerOfGame;}

    public int getGoalsL() {return goalsL;}
    public void setGoalsL(int goalsL) {this.goalsL = goalsL;}

    public int getGoalsV() {return goalsV;}
    public void setGoalsV(int goalsV) {this.goalsV = goalsV;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

//    public Calendar getHour() {return hour;}
//    public void setHour(Calendar hour) {this.hour = hour;}
}
