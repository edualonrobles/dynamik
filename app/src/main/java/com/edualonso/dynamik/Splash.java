package com.edualonso.dynamik;

/**
 * Created by edu_g on 04/07/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.edualonso.dynamik.activities.LoginActivity;
import com.edualonso.dynamik.activities.MainActivity;
import com.edualonso.dynamik.model.MyModel;

/**
 * Esta clase se utiliza para mostrar la pantalla principal de la aplicación
 */

public class Splash extends Activity {

    /** Duration of wait **/

    public static final String MyPREFERENCES = "MyPreferences";
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    CreateTestingData createTestingData;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(icicle);
        setContentView(R.layout.splash);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();

        //Create Testing Data
        //createTestingData = new CreateTestingData();

        MyModel.getInstace();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        //----------Si SharedPreferences and checkButton = true ------> AutoLogin

        if (prefs.getString("userRembember","").equals("remember")) {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }




        // ------------else -->

    }

}