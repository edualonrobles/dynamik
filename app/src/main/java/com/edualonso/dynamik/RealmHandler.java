package com.edualonso.dynamik;

/**
 * Created by edu_g on 10/07/2017.
 */

//Clase que contiene todos los métodos de Realm

public class RealmHandler {
/*



        //---------------User-------------

    //Cuando un usuario se registra

    public static User createUser(String name, String password, String email){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = realm.createObject(User.class);
        user.setName(name);
        user.setEmail(email);

        int nextID= (realm.where(User.class).max("id").intValue()+1);
        user.setIdUser(nextID);
        realm.commitTransaction();

        return user;
    }


    // Cuando queremos recuperar un usuario

    public static User searchUser(String userName, String password){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> results = realm.where(User.class)
                .equalTo(User.USERNAME, userName)
                .or()
                .equalTo(User.PASSWORD, password)
                .findAll();
        if (results.size() != 0) {
            return results.get(0);
        }else {
            return null;
        }
    }

    // Para saber si un usuario existe en Realm
    public static boolean isNameUsed(String userName){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> results = realm.where(User.class)
                .equalTo(User.USERNAME, userName)
                .findAll();
        if (results.size() == 0) {
            return false;
        }else {
            return true;
        }
    }

    public static User addTeamToUser(User u , Team t){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmList<Team> mList = u.getMyTeams();
        mList.add(t);
        u.setMyTeams(mList);
        realm.copyToRealmOrUpdate(u);
        realm.commitTransaction();
        return u;
    }


    public static RealmResults<User> searchUsersInTeam(int teamId){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> results = realm.where(User.class)
                .equalTo("id", teamId)
                .findAll();
        if (results.size() != 0) {
            return results;
        }else {
            return null;
        }
    }


    //-----------------Team----------------

    //Para añadir un usuario a un equipo

    public static Team addPersonToTeam(Player p , Team t ){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        t.getTeamMembers().add(p);
        realm.commitTransaction();

        return t;
    }




    //Cuando queremos crear un equipo en Realm

    public static Team createTeam(String teamName, String city, int position, int photo){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Team t = realm.createObject(Team.class);
        t.setTeamName(teamName);
        t.setCity(city);
        t.setPosition(position);
        t.setPhoto(photo);

        int nextID= (realm.where(Team.class).max("id").intValue()+1);
        t.setIdTeam(nextID);
        realm.commitTransaction();

        return t;
    }


    // Cuando queremos recuperar un equipo de Realm

    public static Team searchTeam(String teamName){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Team> results = realm.where(Team.class)
                .equalTo(Team.TEAMNAME, teamName)
                .findAll();
        if (results.size() != 0) {
            return results.get(0);
        }else {
            return null;
        }
    }

        //--------------Player----------

    public static Player createPerson(String name,String surname,int age){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Player person = realm.createObject(Player.class);
        person.setName(name);
        person.setSurname(surname);
        person.setAge(age);

        int nextID= (realm.where(Player.class).max("id").intValue()+1);
        person.setIdPerson(nextID);
        realm.commitTransaction();

        return person;
    }

*/

}
