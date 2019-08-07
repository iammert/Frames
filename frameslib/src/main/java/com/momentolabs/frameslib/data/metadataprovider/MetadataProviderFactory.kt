package com.momentolabs.frameslib.data.metadataprovider

import com.momentolabs.frameslib.data.metadataprovider.ProviderType.*

object MetadataProviderFactory {

    fun get(providerType: ProviderType, path: String): VideoMetaDataProvider {
        return when (providerType) {
            FFMPEG -> FFmpegVideoMetaDataProvider(path)
        }
    }
}