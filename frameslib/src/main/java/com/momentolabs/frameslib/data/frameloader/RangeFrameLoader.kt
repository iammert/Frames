package com.momentolabs.frameslib.data.frameloader

import com.momentolabs.frameslib.data.metadataprovider.VideoMetaDataProvider
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import io.reactivex.Observable

class RangeFrameLoader(videoMetaDataProvider: VideoMetaDataProvider) : FrameLoader<FrameRetrieveRequest.RangeFrameRequest> {

    override fun loadFrames(frameRetrieveRequest: FrameRetrieveRequest.RangeFrameRequest): Observable<FramesResource> {
        return Observable.create {

        }
    }

}