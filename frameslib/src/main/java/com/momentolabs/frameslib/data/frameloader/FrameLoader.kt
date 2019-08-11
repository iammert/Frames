package com.momentolabs.frameslib.data.frameloader

import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import io.reactivex.Observable

interface FrameLoader<T : FrameRetrieveRequest> {

    fun loadFrames(frameRetrieveRequest: T): Observable<FramesResource>
}