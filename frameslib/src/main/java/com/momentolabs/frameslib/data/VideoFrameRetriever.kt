package com.momentolabs.frameslib.data

import com.momentolabs.frameslib.data.frameloader.*
import com.momentolabs.frameslib.data.metadataprovider.MetadataProviderFactory
import com.momentolabs.frameslib.data.metadataprovider.ProviderType
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import io.reactivex.Observable

class VideoFrameRetriever(private val providerType: ProviderType = ProviderType.FFMPEG) {

    fun retrieveFrames(frameRetrieveRequest: FrameRetrieveRequest): Observable<FramesResource> {

        val videoMetadataProvider = MetadataProviderFactory.get(
            providerType = providerType,
            path = frameRetrieveRequest.videoPath
        )

        return when (frameRetrieveRequest) {
            is FrameRetrieveRequest.MultipleFrameRequest ->
                MultipleFrameLoader(videoMetadataProvider)
                    .loadFrames(frameRetrieveRequest)
            is FrameRetrieveRequest.RangeFrameRequest ->
                RangeFrameLoader(videoMetadataProvider)
                    .loadFrames(frameRetrieveRequest)
            is FrameRetrieveRequest.SingleFrameRequest ->
                SingleFrameLoader(videoMetadataProvider)
                    .loadFrames(frameRetrieveRequest)
        }
    }
}