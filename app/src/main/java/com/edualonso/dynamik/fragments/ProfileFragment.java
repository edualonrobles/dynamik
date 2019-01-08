package com.edualonso.dynamik.fragments;

/**
 * Created by edu_g on 04/07/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.model.user.User;


/**
 * Esta clase sirve para representar el rootFragment "MiPerfil"
 */

public class ProfileFragment extends BaseFragment {

    private View myDataButton,changePassButton,logoutButton,inviteButton;
    private ProfileFragment.MyActivityListener listener;
    private User u ;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;


    private long mLastClickTime = 0;

    public ProfileFragment() {
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Perfil";
    }

    public static ProfileFragment newInstance(ProfileFragment.MyActivityListener listener){

        ProfileFragment p = new ProfileFragment();
        if(listener!=null){
            p.listener=listener;
        }
        return p;
    }

    public static ProfileFragment newProfileFragment(User u){
        ProfileFragment p = new ProfileFragment();
        p.u = u;
        return p;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changePassButton = view.findViewById(R.id.button_changePass);
        myDataButton = view.findViewById(R.id.button_changeData);
        logoutButton = view.findViewById(R.id.button_logout);
        inviteButton = view.findViewById(R.id.button_invite);



        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (listener!=null){
                    listener.onClickChangePass();
                }
            }
        });
        myDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if(listener!=null){
                    listener.onClickChangeData();
                }
            }
        });

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.edualonso.juegazo&ah=SGtFTFUyw7oD1vkheebcXoFA_Qc&hl=es&referrer=my_referrer_finally_works_fine");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                //Limpiamos los datos de sesión
                sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                edit = sharedPreferences.edit();
                sharedPreferences.edit().clear().commit(); // .remove("name");

                Intent intent = new Intent(getActivity().getApplication(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public interface MyActivityListener{
        void onClickChangeData();
        void onClickChangePass();
    }
}

