package com.momentolabs.frameslib.data.metadataprovider

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC
import android.os.Build

class AndroidNativeMetadataProvider(val path: String) : VideoMetaDataProvider {

    private val mediaMetadataRetriever = MediaMetadataRetriever()
        .apply {
            setDataSource(path)
        }

    override fun getVideoDuration(): Long {
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
    }

    override fun getFrameAt(frameInMillis: Long, width: Int, height: Int): Bitmap? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            return mediaMetadataRetriever.getScaledFrameAtTime(frameInMillis, OPTION_CLOSEST_SYNC, width, height)
        } else {
            val frameBitmap = mediaMetadataRetriever.getFrameAtTime(frameInMillis, OPTION_CLOSEST_SYNC)
            val bitmapWidth = frameBitmap.width
            val bitmapHeight = frameBitmap.height
            val min = Math.min(width, height)
            val bitmapMin = Math.min(bitmapWidth.toFloat(), bitmapHeight.toFloat())
            val scale = min / bitmapMin
            val scaledBitmap = Bitmap.createScaledBitmap(
                frameBitmap,
                (bitmapWidth * scale).toInt(),
                (bitmapHeight * scale).toInt(),
                true
            )
            frameBitmap.recycle()
            return scaledBitmap
        }
    }

}