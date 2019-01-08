package com.edualonso.dynamik.utils

import android.support.v7.util.DiffUtil

/**
 * Created by Eduardo Alonso Robles on 10/12/2018.
 * Dynamik.
 */
/**
 * Esta clase contiene métodos que ofrecen una funcionalidad que puede ser útil en la aplicación.
 */

class DefaultItemCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(p0: T, p1: T): Boolean = p0 == p1
    override fun areContentsTheSame(p0: T, p1: T): Boolean = areItemsTheSame(p0, p1)
}