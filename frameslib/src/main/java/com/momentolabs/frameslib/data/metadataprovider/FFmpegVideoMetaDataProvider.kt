package com.momentolabs.frameslib.data.metadataprovider

import android.graphics.Bitmap
import wseemann.media.FFmpegMediaMetadataRetriever
import java.lang.Exception
import java.lang.IllegalStateException

class FFmpegVideoMetaDataProvider(path: String) : VideoMetaDataProvider {

    private val fFmpegMediaMetadataRetriever = FFmpegMediaMetadataRetriever()

    init {
        fFmpegMediaMetadataRetriever.setDataSource(path)
    }

    override fun getVideoDuration(): Long {
        return fFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
    }

    override fun getFrameAt(frameInMillis: Long, width: Int, height: Int): Bitmap? {
        try {
            return fFmpegMediaMetadataRetriever.getScaledFrameAtTime(
                frameInMillis * 1000,
                FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC,
                width,
                height
            )
        } catch (exception: Exception) {
            throw IllegalStateException("Can not get scaled frame from ffmpeg retriever")
        }
    }

}