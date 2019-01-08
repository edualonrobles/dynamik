package com.edualonso.dynamik.fragments

import android.graphics.Bitmap
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

/**
 * Created by Eduardo Alonso Robles on 10/12/2018.
 * Dynamik.
 */

/**
 * Esta clase sirve para subir cada una de las fotos asociadas a cada equipo.
 */

class UploadPhotoTask {

    fun execute(teamId: String, bitmap: Bitmap, callback: () -> Unit) {
        val photoName = System.currentTimeMillis().toString()
        /*First we save to storage and then save it to DB*/
        val storageRef = FirebaseStorage.getInstance().reference
            .child("$STORAGE_TEAM_PHOTOS_REF/$teamId")
        val imageRef = storageRef.child("$photoName.jpg")
        val data = ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
        }.toByteArray()
        /*Create the task that will upload the photo to FirebaseStorage*/
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            callback()
        }.addOnSuccessListener {
            /*After a successful upload, get its downloadUrl and save it to FirebaseDatabase*/
            it.metadata?.reference?.downloadUrl?.addOnCompleteListener { task ->
                task.result?.let { uri ->
                    val dbRef = FirebaseDatabase.getInstance().reference
                    val teamNode = dbRef.child("$DB_TEAM_PHOTOS_REF/$teamId/$photoName")
                    teamNode.setValue(uri.toString()) { _, _ -> callback() }
                } ?: run(callback)
            }
        }
    }

    companion object {
        private const val DB_TEAM_PHOTOS_REF = "TeamPhotos"
        private const val STORAGE_TEAM_PHOTOS_REF = "images"
    }
}