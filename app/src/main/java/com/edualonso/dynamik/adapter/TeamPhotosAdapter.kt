package com.edualonso.dynamik.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.edualonso.dynamik.R
import com.edualonso.dynamik.utils.calculateDiff


/**
 * Created by Eduardo Alonso Robles on 10/12/2018.
 * Dynamik.
 */

/**
 * Esta clase sirve para mostrar cada una de las fotos asociadas a un equipo.
 */
class TeamPhotosAdapter(private val onPhotoClicked: (View, Int, String) -> Unit) :
    RecyclerView.Adapter<TeamPhotosAdapter.PhotoViewHolder>() {

    private var data = emptyList<String>()

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(container.context).inflate(
                R.layout.item_team_photo,
                container,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoUrl = data[position]
        Glide.with(holder.itemView.context).load(photoUrl).into(holder.itemView as ImageView)
    }

    fun onPhotos(photos: List<String>) {
        data.calculateDiff(photos).dispatchUpdatesTo(this)
        data = photos
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onPhotoClicked(itemView, adapterPosition, data[adapterPosition])
                }
            }
        }
    }
}