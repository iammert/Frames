package com.momentolabs.frameslib.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView

class FrameImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ImageView(context, attrs, defStyleAttr) {

    private var isGrayColorSet = false

    override fun onDraw(canvas: Canvas?) {
        if (drawable == null) {
            setBackgroundColor(Color.GRAY)
            return
        }

        if (drawable is BitmapDrawable) {
            if ((drawable as BitmapDrawable).bitmap == null || (drawable as BitmapDrawable).bitmap.isRecycled) {
                if (isGrayColorSet.not()) {
                    setBackgroundColor(Color.GRAY)
                    isGrayColorSet = true
                }
                return
            }
        }

        super.onDraw(canvas)
    }
}