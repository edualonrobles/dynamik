package com.edualonso.dynamik.fragments

import android.support.v4.app.Fragment
import com.edualonso.dynamik.activities.ViewTitleOwner

/**
 * Created by Eduardo Alonso Robles on 19/12/2018.
 * Dynamik.
 */

/**
 * Esta clase sirve para que todos los fragments implementen los métodos propios comunes a todos
 * los fragments
 */
abstract class BaseFragment : Fragment(), ViewTitleOwner {

    final override fun setViewTitle(title: String) {
        (context as? ViewTitleOwner)?.setViewTitle(title)
    }

    override fun getViewTitle(): String? = null
}