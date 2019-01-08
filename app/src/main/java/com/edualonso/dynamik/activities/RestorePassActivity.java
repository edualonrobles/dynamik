package com.edualonso.dynamik.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edualonso.dynamik.R;
import com.edualonso.dynamik.model.MyModel;
import com.edualonso.dynamik.model.user.RestorePass;
import com.edualonso.dynamik.model.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by edu_g on 13/07/2017.
 */

/**
 * RestorePassActivity: Esta clase recoge la funcionalidad de resetear la contraseña
 */

public class RestorePassActivity extends AppCompatActivity {


    private Button buttonRestore;
    private EditText teRestore;
    private String pass,codPass,email;
    private MyModel model;
    private List<User>temp;
    private boolean findEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_pass);

        model = MyModel.getInstace();
        temp = model.getUsers();

        findEmail=false;

        RestorePass restorePass = new RestorePass();
        pass = restorePass.generateNewPassword();
        codPass = restorePass.codifyPass(pass);

        final DatabaseReference dbUsers =
                FirebaseDatabase.getInstance().getReference().child("Users");


        buttonRestore = (Button) findViewById(R.id.button_restore);
        teRestore = (EditText) findViewById(R.id.te_restore);



        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = teRestore.getText().toString().trim();

                for(User u : temp){
                    if(email.equals(u.getEmail())){
                        u.setPass(codPass);
                        findEmail=true;
                        dbUsers.child(u.getId()).child("pass").setValue(codPass);

                    }else{

                    }
                }
                if(!findEmail){
                    Toast toast2=Toast.makeText(RestorePassActivity.this,"El email introducido no existe",Toast.LENGTH_SHORT);
                    toast2.show();
                }else{
                    Toast toast2=Toast.makeText(RestorePassActivity.this,"Contraseña cambiada, revise su correo electrónico",Toast.LENGTH_SHORT);
                    toast2.show();
                    RestorePassActivity.this.finish();
                }
            }
        });
    }
}
