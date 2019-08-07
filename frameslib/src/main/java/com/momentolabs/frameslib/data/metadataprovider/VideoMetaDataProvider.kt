package com.momentolabs.frameslib.data.metadataprovider

import android.graphics.Bitmap
import io.reactivex.Single

interface VideoMetaDataProvider {

    fun getVideoDuration(): Long

    fun getFrameAt(frameInMillis: Long, width: Int, height: Int): Bitmap?
}