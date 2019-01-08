package com.edualonso.dynamik.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.user.User;
import com.edualonso.dynamik.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by edu_g on 17/07/2017.
 */

/**
 * Esta clase sirve para mostrar la funcionalidad de cambiar la contraseña del usuario.
 */

public class ChangePass extends BaseFragment {

    private static User myUser;
    private MyModel model;
    private List<User> users;
    private Utils utils;
    private String pass,confPass,myPass;
    private EditText et_pass, et_confPass,et_myPass;
    private Button button_changePass;
    private DatabaseReference dbUsers;
    private boolean passOk;


    public static ChangePass newInstance(User u){
        myUser = u;
        ChangePass changePass = new ChangePass();
        return changePass;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getViewTitle() {
        return "Cambiar contraseña";
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = MyModel.getInstace();
        users = model.getUsers();

        utils = new Utils();


        //-------------Widgets-------------

        et_myPass = (EditText) view.findViewById(R.id.te_myPass);
        et_pass = (EditText) view.findViewById(R.id.te_changePass);
        et_confPass = (EditText) view.findViewById(R.id.te_changeConfPass);
        button_changePass = (Button) view.findViewById(R.id.button_changeMyPass);

        //------------Firebase-------------

        dbUsers = FirebaseDatabase.getInstance().getReference().child("Users");


        button_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = et_pass.getText().toString().trim();
                confPass = et_confPass.getText().toString().trim();
                myPass = et_myPass.getText().toString().trim();

                passOk=true;


                if(!myPass.equals("")){
                    if(myPass.equals(myUser.getPass())){
                        passOk=true;
                    }else{
                        Toast toast = Toast.makeText(getActivity(),"La contraseña no es correcta",Toast.LENGTH_SHORT);
                        toast.show();
                        passOk=false;
                    }
                }else{
                    Toast toast = Toast.makeText(getActivity(),"La contraseña no puede estar vacía",Toast.LENGTH_SHORT);
                    toast.show();
                    passOk=false;
                }

                if(pass.equals("") || confPass.equals("")) {

                    Toast toast = Toast.makeText(getActivity(),"Las nuevas contraseñas no pueden estar vacía",Toast.LENGTH_SHORT);
                    toast.show();
                    passOk=false;

                }

                if(pass.equals(confPass) && passOk){

                    String key = myUser.getId();
                    pass=utils.codifyPass(pass);
                    myUser.setPass(pass);

                    //Actualizamos el valor en BD
                    dbUsers.child(key).setValue(myUser);

                    //Actualizamos el valor en local
                    for(int i=0;i<users.size();i++){
                        if(users.get(i).getId().equals(key)){
                            users.set(i,myUser);
                        }
                    }
                    SharedPreferences prefs = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("pass",pass);



                }else{
                    Toast toast = Toast.makeText(getActivity(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_changepass,container,false);
    }
}
