package com.example.edualonso.miaplicacion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast

/**
 * Created by edu_g on 18/07/2017.
 */


/**
 * Esta clase se utiliza para crear poder utilizar referidos en las invitaciones.
 */
class Tracker : BroadcastReceiver(){

    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.toast("Referer is: " + p1?.extras?.getString("referrer", "NULL"))
    }


}