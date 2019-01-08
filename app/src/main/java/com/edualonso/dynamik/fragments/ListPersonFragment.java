package com.edualonso.dynamik.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.adapter.PlayerListAdapter;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Player;
import com.edualonso.dynamik.model.Team;
import com.edualonso.dynamik.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 04/07/2017.
 */

/**
 * Esta clase sirve para mostrar el listado de personas asociadas a cada equipo.
 */

public class ListPersonFragment extends BaseFragment implements PlayerListAdapter.OnImageClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Team> teams;
    private List<User> users;
    private List<Player> players;
    private ArrayList<Player> myPlayerData;
    MyActivityListener listener;
    private MyModel myModel;
    private static Team myTeam;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;



    public static ListPersonFragment newInstance(Team t, MyActivityListener listener){
        ListPersonFragment f = new ListPersonFragment();
        f.listener = listener;
        f.myPlayerData = new ArrayList<>();
        myTeam = t;
        return f;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Miembros";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listperson, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //----------------- Model ---------------------

        myModel = MyModel.getInstace();
        teams = myModel.getTeams();
        users = myModel.getUsers();
        players = myModel.getPlayers();

        prefs = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor= prefs.edit();



        mRecyclerView = (RecyclerView) view.findViewById(R.id.person_list);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        final DatabaseReference dbTeams =
//                FirebaseDatabase.getInstance().getReference().child("Persons");
//        dbTeams.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                //Player p = dataSnapshot.getValue(Player.class);
//                //System.out.println(p.getTeamName());
//
//                    //myPlayerData.add(p);
//                    //mRecyclerView.setAdapter(mAdapter);
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        if(myPlayerData.size()==0) {
            for (User u : users) {
                if (u.getId().equals(prefs.getString("id", ""))) {    //Buscar usuario
                    //for (String t : u.getTeams()) {
                    for (String team : u.getTeams()) {
                        if (team.equals(myTeam.getIdTeam())) {
                            for (Player p : players) {
                                if (p.getTeam().equals(team)) {
                                    myPlayerData.add(p);
                                    //mAdapter = new PlayerListAdapter(getActivity(), myPlayerData, this);
                                    //mRecyclerView.setAdapter(mAdapter);

                                }
                            }
                        }
                    }

                    //}
                }
            }
        }

        mAdapter = new PlayerListAdapter(getActivity(), myPlayerData, this);
        mRecyclerView.setAdapter(mAdapter);



        //mAdapter = new PlayerListAdapter(getActivity(), myPlayerData, this);


    }

    @Override
    public void omImageClick(Player p) {
        listener.goPersonDetail(p);
    }


    public interface MyActivityListener{
        void goPersonDetail(Player p);
    }
}
