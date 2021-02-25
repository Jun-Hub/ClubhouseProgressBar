package com.github.joon.chprogressbar

import android.graphics.*
import android.graphics.drawable.Drawable

class DotDrawable(private val radius: Float,
                  var paint: Paint) : Drawable() {

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), radius, paint)
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}