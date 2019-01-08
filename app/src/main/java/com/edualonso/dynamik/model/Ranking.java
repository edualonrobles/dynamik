package com.edualonso.dynamik.model;

import com.edualonso.dynamik.model.user.Competition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 21/07/2017.
 */

/**
 * DataClass Ranking : Representa la información que debe contener el objeto Ranking
 */

public class Ranking {

    String idCompetition;
    String name;
    int position,gamesPlayed,points,wins,draws,loses,goalsF,goalsA;
    private MyModel model;
    List<Game> games;


    public Ranking(){
        model = MyModel.getInstace();
        position = 0;
        points = 0;
        wins = 0;
        draws = 0;
        loses = 0;
        goalsF = 0;
        goalsA = 0;
    }

    public Ranking(String idCompetition, String name, int position, int gamesPlayed, int points, int wins, int draws, int loses, int goalsF, int goalsA) {
        MyModel.getInstace();
        this.idCompetition = idCompetition;
        this.name = name;
        this.position = position;
        this.gamesPlayed = gamesPlayed;
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.goalsF = goalsF;
        this.goalsA = goalsA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoalsA() {
        return goalsA;
    }

    public void setGoalsA(int goalsA) {
        this.goalsA = goalsA;
    }

    public String getIdCompetition() {return idCompetition;}
    public void setIdCompetition(String idCompetition) {this.idCompetition = idCompetition;}


    public int getPosition() {return position;}
    public void setPosition(int position) {this.position = position;}

    public int getGamesPlayed() {return gamesPlayed;}
    public void setGamesPlayed(int gamesPlayed) {this.gamesPlayed = gamesPlayed;}

    public int getPoints() {return points;}
    public void setPoints(int points) {this.points = points;}

    public int getWins() {return wins;}
    public void setWins(int wins) {this.wins = wins;}

    public int getDraws() {return draws;}
    public void setDraws(int draws) {this.draws = draws;}

    public int getLoses() {return loses;}
    public void setLoses(int loses) {this.loses = loses;}

    public int getGoalsF() {return goalsF;}
    public void setGoalsF(int goalsF) {this.goalsF = goalsF;}


    /*public List<Ranking> generateRanking(List<Team> teams, Competition competition){

        List<Ranking> rankings = new ArrayList<>();
        games = model.getGames();
        for(Team t : teams) {
            Ranking ranking = new Ranking();
            for (String g : competition.getGames()) {
                for(Game game : games){
                    if(game.getIdGame().equals(g)){
                        if (t.getIdTeam().equals(game.getIdTeamL())) {
                            ranking.setTeam(t);
                            ranking.setPosition(1);
                            ranking.setGamesPlayed(ranking.getGamesPlayed()+1);
                            ranking.setGoalsF(ranking.getGoalsF()+game.getGoalsL());
                            ranking.setGolesA(ranking.getGolesA()+game.getGoalsV());
                            ranking.setDifferenceFA(ranking.getGoalsF()-ranking.getGolesA());
                            if(game.getGoalsL()>game.getGoalsV()){
                                ranking.setPoints(ranking.getPoints()+3);
                                ranking.setWins(ranking.getWins()+1);
                            }else if(game.getGoalsL()== game.getGoalsV()){
                                ranking.setPoints(ranking.getPoints()+1);
                                ranking.setDraws(ranking.getDraws()+1);
                            }else{
                                ranking.setLoses(ranking.getLoses()+1);
                            }

                        }else if(t.getIdTeam().equals(game.getIdTeamV())){
                            ranking.setTeam(t);
                            ranking.setPosition(1);
                            ranking.setGamesPlayed(ranking.getGamesPlayed()+1);
                            ranking.setGoalsF(ranking.getGoalsF()+game.getGoalsV());
                            ranking.setGolesA(ranking.getGolesA()+game.getGoalsL());
                            ranking.setDifferenceFA(ranking.getGoalsF()-ranking.getGolesA());
                            if(game.getGoalsL()<game.getGoalsV()){
                                ranking.setPoints(ranking.getPoints()+3);
                                ranking.setWins(ranking.getWins()+1);
                            }else if(game.getGoalsL()== game.getGoalsV()){
                                ranking.setPoints(ranking.getPoints()+1);
                                ranking.setDraws(ranking.getDraws()+1);
                            }else{
                                ranking.setLoses(ranking.getLoses()+1);
                            }
                        }
                    }
                }

            }
            rankings.add(ranking);
        }

        //Falta ordenar ranking
        return rankings;
    }


    public List<Ranking> ordenarRankings(List<Ranking> rankings){

        Ranking aux;
        for(int i=0;i<rankings.size()-1;i++){
            for(int j=0;j<rankings.size()-i-1;j++){
                if(rankings.get(j+1).getPoints()>rankings.get(j).getPoints()){
                    aux = rankings.get(j+1);
                    rankings.set(j+1,rankings.get(j));
                    rankings.set(j,aux);
                }else if(rankings.get(j+1).getPoints()==rankings.get(j).getPoints()){
                    if(rankings.get(j+1).getDifferenceFA()>rankings.get(j).getDifferenceFA()){
                        aux = rankings.get(j+1);
                        rankings.set(j+1,rankings.get(j));
                        rankings.set(j,aux);
                    }else if(rankings.get(j+1).getDifferenceFA()>rankings.get(j).getDifferenceFA()){
                        if(rankings.get(j+1).getGoalsF()>rankings.get(j).getGolesA()){
                            aux = rankings.get(j+1);
                            rankings.set(j+1,rankings.get(j));
                            rankings.set(j,aux);
                        }
                    }
                }
            }
        }
        for(int k=0;k<rankings.size();k++){
            rankings.get(k).setPosition(k+1);
        }

        return rankings;
    }*/

}
