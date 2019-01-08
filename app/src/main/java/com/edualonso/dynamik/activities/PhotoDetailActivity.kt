package com.edualonso.dynamik.activities

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.edualonso.dynamik.R
import kotlinx.android.synthetic.main.activity_photo_detail.*


/**
 * PhotoDetail: Esta clase la funcionalidad del detalle de una foto
 */

class PhotoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        ViewCompat.setTransitionName(photoView, intent.getStringExtra(EXTRA_TRANSITION_NAME))
        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_PHOTO_URL))
            .into(object : SimpleTarget<GlideDrawable>() {
                override fun onResourceReady(
                    resource: GlideDrawable?,
                    glideAnimation: GlideAnimation<in GlideDrawable>?
                ) {
                    photoView.setImageDrawable(resource)
                }
            })
    }

    companion object {
        const val EXTRA_PHOTO_URL = "extra:photo_url"
        const val EXTRA_TRANSITION_NAME = "extra:transition_name"
    }
}
