package com.edualonso.dynamik;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.io.File;


/**
 * Created by edu_g on 10/07/2017.
 */

/**
 * Clase Application del proyecto. Se utiliza para inicializar diferentes componentes
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //CreateTestingData testingData = new CreateTestingData();
        //Realm.init(this);

        //Firebase
        FirebaseApp.initializeApp(this);


        //AddInfo addInfo = new AddInfo(getBaseContext());

        //--------------Twitter---------------------
        //Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("0lOHMnyqY78Pp3smjhJNUSNjs", "htmMSIoTZplUufft88oLZEe6w1615HdF20RteV6uAM97NgEjzt"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        //RealmConfiguration config = new RealmConfiguration.Builder().build();
        //Realm.setDefaultConfiguration(config);
    }

//    RealmConfiguration config = new RealmConfiguration.Builder()
//            .name("myrealm.realm")
//            .schemaVersion(2)
//            .build();
//
//    Realm realm = Realm.getInstance(config);
}
