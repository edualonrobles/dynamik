package com.edualonso.dynamik.model.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by edu_g on 13/07/2017.
 */

/**
 * DataClass RestorePass : Representa la información que debe contener el objeto RestorePass
 */

public class RestorePass {

    private String email;
    public static String NUMEROS = "0123456789";

    public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    public static String ESPECIALES = "ñÑ";

    int length = 10;

    public String generateNewPassword(){
        return generateNewPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
    }


    public String generateNewPassword(String key, int length){

        String newPass = "";

        for (int i = 0; i < length; i++) {
            newPass+=(key.charAt((int)(Math.random() * key.length())));
        }

        return newPass;
    }

    public String codifyPass(String pass){

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
        String codPass = sb.toString();
        return codPass;
    }

}
