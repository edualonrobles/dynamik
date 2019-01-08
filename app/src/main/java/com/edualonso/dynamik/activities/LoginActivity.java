package com.edualonso.dynamik.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.user.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu_g on 05/07/2017.
 */

/**
 * LoginActivity: Esta clase recoge la funcionalidad del Login del usuario.
 */

public class LoginActivity extends AppCompatActivity {


    public static final String MyPREFERENCES = "MyPreferences";

    private List<String> listKey;
    private List<Object> listValue;
    private EditText et_userName,et_pass;
    private Button buttonLogin,buttonSingUp;
    private CheckBox checkBoxSaveUser;
    private View llRestorePass;

    String name,pass;

    private boolean userFound =false;
    private ValueEventListener eventListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        listKey = new ArrayList<String>();
        listValue = new ArrayList<Object>();

        et_userName = findViewById(R.id.te_userLogin);
        et_pass = findViewById(R.id.te_passLogin);

        final String user;


        //----------------------------Loggin Button ---------------------------

        buttonLogin = findViewById(R.id.button_login);
        checkBoxSaveUser = findViewById(R.id.cb_saveUser);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = et_userName.getText().toString().trim();
                pass = et_pass.getText().toString().trim();
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                byte[] hash = md.digest(pass.getBytes());
                StringBuffer sb = new StringBuffer();

                for (byte b : hash) {
                    sb.append(String.format("%02x", b));
                }

                //                        System.out.println(u.getPass());

                //                        for (String value : listKey){
                //                            if(value.equals(name)){
                //                                nameFounded=true;
                //                            }
                //                        }
                pass = sb.toString();

                MyModel model = MyModel.getInstace();
                List<User> tempList = model.getUsers();
                for (User u : tempList) {
                    if (name.equals(u.getName()) && pass.equals(u.getPass())) {
                        userFound = true;
                        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString("id", u.getId());
                        editor.putString("user", name);
                        editor.putString("email", u.getEmail());
                        editor.putString("pass", u.getPass());
                        editor.putString("photo", String.valueOf(u.getPhoto()));
                        if (checkBoxSaveUser.isChecked()) {
                            //editor.putBoolean("userRemember",true);
                            editor.putString("userRembember", "remember");
                        } else {
                            editor.putString("userRembember", "");
                            //editor.putBoolean("userRemember","");
                        }
                        Log.d("Tag", et_userName.getText().toString().trim());
                        //editor.putString("pass",et_pass.getText().toString().trim());
                        editor.commit();


                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(mainIntent);
                        LoginActivity.this.finish();
                    } else if (name.equals(u.getEmail()) && pass.equals(u.getPass())) {
                        userFound = true;
                        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString("id", u.getId());
                        editor.putString("user", u.getName());
                        editor.putString("email", name);
                        editor.putString("pass", u.getPass());
                        editor.putString("photo", String.valueOf(u.getPhoto()));
                        editor.commit();


                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(mainIntent);
                        LoginActivity.this.finish();
                    }
                }

                if (!userFound) {
                    Toast toast1 = Toast.makeText(LoginActivity.this, "Error,el usuario o la contraseña no son correctas", Toast.LENGTH_SHORT);
                    toast1.show();
                }


                final DatabaseReference dbTeams =
                        FirebaseDatabase.getInstance().getReference().child("Users");//.child("Users");
                Query queryRef = dbTeams.orderByChild("Users");
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                        listKey.add(dataSnapshot.getKey());

                        //listValue.add(dataSnapshot.getValue());
                        //for (Object o : listKey) {

                        //User u = User.class.cast(o) ;
                        //  System.out.println(u);

//                            User u = dataSnapshot.getValue(User.class);
                        //System.out.println("Name: " + u.getName() + " ------- Pass " + u.getPass());


                        //System.out.println("Voy a comprobar User:" + name + " Pass " + pass);
                        //System.out.println("Pass "+pass);

//                            if (name.equals(u.getName()) && pass.equals(u.getPass())) {
//                                userFound = true;
//                                SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = prefs.edit();
//
//                                editor.putString("user",name);
//                                Log.d("Tag",et_userName.getText().toString().trim());
//                                //editor.putString("pass",et_pass.getText().toString().trim());
//                                editor.commit();
//
//
//                                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//                                LoginActivity.this.startActivity(mainIntent);
//                                LoginActivity.this.finish();

//                            }else{
//                                //Toast toast1=Toast.makeText(LoginActivity.this,"Error,el usuario o la contraseña no son correctas",Toast.LENGTH_SHORT);
//                                //toast1.show();
//                            }
                        //}
                        //}

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        // -----------------------SingUp Button ----------------------------------


        buttonSingUp = findViewById(R.id.button_go_sing_up);
        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, SingUpActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
        });

        //--------------------------RESTORE PASSWORD -------------------------------

        llRestorePass = findViewById(R.id.ll_restore);
        llRestorePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RestorePassActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });


    }
}

