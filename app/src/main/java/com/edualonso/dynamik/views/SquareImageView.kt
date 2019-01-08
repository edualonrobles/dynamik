package com.edualonso.dynamik.views

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * Created by Eduardo Alonso Robles on 10/12/2018.
 * Dynamik.
 */

/**
 *  Esta clase se utiliza para crear por código la vista de las imágenes subidas
 */
class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}