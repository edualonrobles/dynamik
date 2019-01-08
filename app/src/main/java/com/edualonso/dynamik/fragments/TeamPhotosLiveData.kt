package com.edualonso.dynamik.fragments

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

/**
 * Created by Eduardo Alonso Robles on 10/12/2018.
 * Dynamik.
 */

/**
 * Esta clase sirve para realizar la consulta a firebase y recuperar las fotos de cada equipo.
 */
class TeamPhotosLiveData(private val teamId: String) : LiveData<List<String>>() {

    private val dbRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }
    private var valueEventListener: ValueEventListener? = null

    override fun onActive() {
        val teamNode = dbRef.child("$TEAM_PHOTOS_REF/$teamId")
        val listener = object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val genericTypeIndicator = object :
                    GenericTypeIndicator<Map<@JvmSuppressWildcards String, @JvmSuppressWildcards String>>() {}
                val photos = data.getValue(genericTypeIndicator) ?: emptyMap()
                value = photos.values.sortedDescending().toList()
            }

            override fun onCancelled(error: DatabaseError) = Unit
        }
        teamNode.addValueEventListener(listener)
        valueEventListener = listener
    }

    override fun onInactive() {
        valueEventListener?.let(dbRef::removeEventListener)
    }

    companion object {
        private const val TEAM_PHOTOS_REF = "TeamPhotos"
    }
}