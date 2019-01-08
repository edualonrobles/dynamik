package com.edualonso.dynamik.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.user.User;
import com.edualonso.dynamik.utils.Utils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by edu_g on 07/07/2017.
 */

/**
 * Esta clase recoge la funcionalidad del registro del usuario
 */

public class SingUpActivity extends AppCompatActivity {

    Button buttonSingUp;
    private EditText editTextUser,editTextEmail,editTextPass,editTextConfPass;
    private boolean mailExist,nameExist,passEqConf;
    private ValueEventListener eventListener;
    private MyModel model;
    private List<User>users;
    private Utils utils;
    private String codPass;
    //private ImageView uImage;
    final int idPhoto = R.drawable.ic_myuserphto;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);



        editTextUser = (EditText) findViewById(R.id.te_user);
        editTextEmail = (EditText) findViewById(R.id.te_email);
        editTextPass = (EditText) findViewById(R.id.te_pass);
        editTextConfPass = (EditText) findViewById(R.id.te_confpass);


       // uImage.setImageResource(id);

        buttonSingUp = (Button) findViewById(R.id.button_sing_up);
        FirebaseApp.initializeApp(this);
        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Model

                model= MyModel.getInstace();
                users= model.getUsers();
                utils = new Utils();

                System.out.println(users);

                //--------------Widgets----------------

                String name = editTextUser.getText().toString().trim();
                String address = editTextEmail.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();
                String confPass = editTextConfPass.getText().toString().trim();


                //  Firebase
                DatabaseReference dbTeams =
                        FirebaseDatabase.getInstance().getReference().child("Users");



                eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        {


                            long uCount = dataSnapshot.getChildrenCount() + 1;


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Error", "Error!", databaseError.toException());
                    }
                };

                //---------Confirmar el acceso------------

                mailExist=true;
                nameExist=true;
                passEqConf=true;

                dbTeams.addListenerForSingleValueEvent(eventListener);



                DatabaseReference dbUsers =
                        FirebaseDatabase.getInstance().getReference()
                                .child("Users");

                        ///------------Codify Pass --------------
//                            MessageDigest md=null;
//                            try {
//                                md = MessageDigest.getInstance("SHA-256");
//                            } catch (NoSuchAlgorithmException e) {
//                                e.printStackTrace();
//                            }
//
//                            byte[] hash = md.digest(pass.getBytes());
//                            StringBuffer sb = new StringBuffer();
//
//                            for(byte b : hash){
//                                sb.append(String.format("%02x",b));
//                            }

                codPass = utils.codifyPass(pass);

                if (name.equals("")) {
                    Toast toast1 = Toast.makeText(SingUpActivity.this, "El campo nombre no puede estar vacio", Toast.LENGTH_SHORT);
                    toast1.show();
                    nameExist = false;
                }
                else if(name.length()<4){
                    Toast toast1 = Toast.makeText(SingUpActivity.this, "El campo nombre debe tener una longitud mínima de 4 caracteres", Toast.LENGTH_SHORT);
                    toast1.show();
                    nameExist = false;

                }else if(name.length()>15){
                    Toast toast1 = Toast.makeText(SingUpActivity.this, "El campo nombre debe tener una longitud maxima de 15 caracteres", Toast.LENGTH_SHORT);
                    toast1.show();
                    nameExist = false;
                }


                if (address.equals("")){
                    Toast toast1 = Toast.makeText(SingUpActivity.this, "El campo Email no puede estar vacio", Toast.LENGTH_SHORT);
                    toast1.show();
                    mailExist = false;
                }else if(!address.contains("@")){
                    Toast toast1 = Toast.makeText(SingUpActivity.this, "El campo Email debe ser del tipo email@example.es", Toast.LENGTH_SHORT);
                    toast1.show();
                    mailExist = false;
                }


                if (pass.equals("") || confPass.equals("")) {
                    Toast toast1 = Toast.makeText(SingUpActivity.this, "Los campos de contraseñas no pueden estar vacios", Toast.LENGTH_SHORT);
                    toast1.show();
                    passEqConf = false;
                }
//                }else{
//                    nameExist=mailExist=passEqConf=true;
//                }



                for(User u : users){
                    if(u.getName()!=null && u.getEmail()!=null) {
                        if (u.getName().equals(name)) {
                            Toast toast1 = Toast.makeText(SingUpActivity.this, "El usuario introducido ya existe", Toast.LENGTH_SHORT);
                            toast1.show();
                            nameExist = false;
                        } else {
                            nameExist = true;
                        }

                        if (u.getEmail().equals(address)) {
                            Toast toast1 = Toast.makeText(SingUpActivity.this, "El email introducido ya existe", Toast.LENGTH_SHORT);
                            toast1.show();
                            mailExist = false;
                        }else{
                            nameExist = true;
                        }
                    }
                    else{
//                        Toast toast1 = Toast.makeText(SingUpActivity.this, "El usuario recuperado está vacio", Toast.LENGTH_SHORT);
//                        toast1.show();
                    }
                }

                if (!pass.equals(confPass)){
                    Toast toast1=Toast.makeText(getApplicationContext(),"Error, las contraseñas no coinciden",Toast.LENGTH_SHORT);
                    toast1.show();
                    passEqConf=false;
                }else{
                    if(pass.length()<6){
                        Toast toast1=Toast.makeText(getApplicationContext(),"Las contraseñas deben de contener al menos 6 caracteres",Toast.LENGTH_SHORT);
                        toast1.show();
                        passEqConf=false;

                    }else if(pass.length()>15){
                        Toast toast1=Toast.makeText(getApplicationContext(),"Las contraseñas pueden contener 15 caracteres como máximo",Toast.LENGTH_SHORT);
                        toast1.show();
                    }else{
                        passEqConf = true;
                    }
                }

                //Creating Player object
                User user = new User();

                //Adding values

                if (passEqConf && nameExist && mailExist){
                    String key = dbUsers.push().getKey();
                    user.setId(key);
                    user.setName(name);
                    user.setEmail(address);
                    user.setPass(codPass);
                    user.setPhoto(idPhoto);
                    //user.setTeams();
//                    Team t = new Team("1");
//                    user.getTeams().add(t.getIdTeam());
                    dbUsers.child(key).setValue(user); dbUsers.child(key).child("Teams").setValue(user.getTeams());

                    Intent mainIntent = new Intent(SingUpActivity.this,LoginActivity.class);
                    SingUpActivity.this.startActivity(mainIntent);
                    SingUpActivity.this.finish();

                }else{
                    Toast toast1=Toast.makeText(getApplicationContext(),"Alguno de los campos no ha sido introducido correctamente",Toast.LENGTH_SHORT);
                    toast1.show();

                }
                    //user.setIdUser(getTaskId());
                    //user.setIdUser();


                    //Storing values to firebase
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("Users").child("id" + uCount).setValue(user);
                //}


            }
        });
    }



}
