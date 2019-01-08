package com.edualonso.dynamik

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 * Created by Edu Alonso on 15/08/2018.
 */
abstract class BaseActivity : AppCompatActivity(),
ConnectionReceiver.ConnectionListener{

    private var mConnectionReceiver : ConnectionReceiver? = null
    private var isBackPressed = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mConnectionReceiver = ConnectionReceiver(this)
    }

    override fun onResume() {
        super.onResume()
        //registerReceiver(mConnectionReceiver, ConnectionReceiver.getIntentFilter())
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mConnectionReceiver);
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setFinishingFromUserInteraction(true)
    }

    fun isFinishingFromUserInteraction() : Boolean {
        return isFinishing && isBackPressed
    }

   fun setFinishingFromUserInteraction(b : Boolean) {
        isBackPressed = b
    }
    override fun onConnection(connected: Boolean, networkName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}