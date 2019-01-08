package com.edualonso.dynamik.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by edu_g on 17/07/2017.
 */

/**
 * Esta clase contiene métodos que ofrecen una funcionalidad que puede ser útil en la aplicación.
 */

public class Utils {


    public String codifyPass(String pass){

        ///------------Codify Pass --------------
        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = md.digest(pass.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : hash){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

    public static StorageReference getTeamSpaceRef(FirebaseStorage storage, String photo){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://miaplicacion-37f77.appspot.com");
        StorageReference imagesRef = storageRef.child("logos");
        return imagesRef.child(String.valueOf(photo +".png"));
    }

    public static SharedPreferences.Editor getSharedPreferences(Activity activity, String preferences, int mode){

        SharedPreferences prefs = activity.getSharedPreferences(preferences, mode);
        return prefs.edit();
    }

    public static DatabaseReference getDatabaseReference(String child){
        return FirebaseDatabase.getInstance().getReference().child(child);
    }

}
