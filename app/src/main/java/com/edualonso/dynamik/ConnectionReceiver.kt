package com.edualonso.dynamik

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.ref.WeakReference


/**
 * Created by Edu Alonso on 15/08/2018.
 */
class ConnectionReceiver(listener : ConnectionListener) : BroadcastReceiver() {

    interface ConnectionListener{
        fun onConnection(connected : Boolean, networkName : String)
    }

    private var listener : WeakReference<ConnectionListener>? = null

    init {
        this.listener = WeakReference(listener)
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        /*if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action){
            listener?.get()?.let {
                val cm : ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo : NetworkInfo? = cm.activeNetworkInfo
                listener.get().onConnection( netInfo!=null && netInfo.isConnected,Utils.getCurrentSsid(context))

            }
        }*/
    }

    fun getIntentFilter() : IntentFilter{
        return IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }

}