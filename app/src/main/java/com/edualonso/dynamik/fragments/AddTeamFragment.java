package com.edualonso.dynamik.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.adapter.AddTeamAdapter;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 05/07/2017.
 */

/**
 * Esta clase recoge la funcionalidad de suscribirse/cancelar suscripción a un equipo.
 */

public class AddTeamFragment  extends BaseFragment implements AddTeamAdapter.OnImageClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Team> myTeamData;
    private static MyModel myModel;
    AddTeamFragment.MyActivityListener listener;



    public static AddTeamFragment newInstance(AddTeamFragment.MyActivityListener listener){
        AddTeamFragment f = new AddTeamFragment();
        f.listener = listener;
        myModel = MyModel.getInstace();
        f.myTeamData = new ArrayList<>();
        return f;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Añadir equipo";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_to_team, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.team_list);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        final DatabaseReference dbTeams =
//                FirebaseDatabase.getInstance().getReference().child("Teams");
//        dbTeams.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Team t =dataSnapshot.getValue(Team.class);
//                //System.out.println(t.getTeamName());
//                myTeamData.add(t);
//
//                mRecyclerView.setAdapter(mAdapter);
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

        myTeamData = myModel.getTeams();

        mAdapter = new AddTeamAdapter(getActivity(), myTeamData, this);
        mRecyclerView.setAdapter(mAdapter);

        //Creo 3 personas
//        myTeamData.add(new Team("Real Madrid","Madrid","21",1,ic_launcher_round));
//        myTeamData.add(new Team("Unionistas","Salamanca","16",2,ic_launcher_round));
//        myTeamData.add(new Team("Calasanz","Salamanca","10",3,ic_launcher_round));



    }

    @Override
    public void omImageClick(Team t) {
        listener.goTeamDetail(t);
    }


    public interface MyActivityListener{
        void goTeamDetail(Team t);
    }
}
