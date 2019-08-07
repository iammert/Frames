package com.momentolabs.frameslib.data.frameloader

import com.momentolabs.frameslib.data.metadataprovider.VideoMetaDataProvider
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import io.reactivex.Observable

class SingleFrameLoader(videoMetaDataProvider: VideoMetaDataProvider) :
    FrameLoader<FrameRetrieveRequest.SingleFrameRequest> {

    override fun loadFrames(frameRetrieveRequest: FrameRetrieveRequest.SingleFrameRequest): Observable<FramesResource> {
        return Observable.create {

        }
    }

}