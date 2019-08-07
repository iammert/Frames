package com.momentolabs.frameslib.data.model

import android.graphics.Bitmap

data class FrameItem(
    val frameIndex: Int = 0,
    val bitmap: Bitmap? = null,
    val fillRatio: Float = 1f,
    val startDuration: Long = 0L,
    val endDuration: Long = 0L
)