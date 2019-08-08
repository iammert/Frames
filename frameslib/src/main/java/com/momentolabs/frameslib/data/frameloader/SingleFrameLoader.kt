package com.momentolabs.frameslib.data.frameloader

import com.momentolabs.frameslib.data.metadataprovider.VideoMetaDataProvider
import com.momentolabs.frameslib.data.model.FrameItem
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import io.reactivex.Observable

class SingleFrameLoader(private val videoMetaDataProvider: VideoMetaDataProvider) :
    FrameLoader<FrameRetrieveRequest.SingleFrameRequest> {

    override fun loadFrames(frameRetrieveRequest: FrameRetrieveRequest.SingleFrameRequest): Observable<FramesResource> {
        return Observable.create { emitter ->
            val validatedDuration =
                if (frameRetrieveRequest.durationInMillis in 0 until videoMetaDataProvider.getVideoDuration()) {
                    frameRetrieveRequest.durationInMillis
                } else {
                    0L
                }

            val emptyFrameItem = FrameItem(
                frameIndex = 0,
                fillRatio = 1.0F,
                startDuration = validatedDuration,
                endDuration = validatedDuration
            )

            emitter.onNext(FramesResource.initialize(arrayListOf(emptyFrameItem)))


            val frameBitmap = videoMetaDataProvider
                .getFrameAt(
                    frameInMillis = validatedDuration,
                    width = frameRetrieveRequest.frameWidth,
                    height = frameRetrieveRequest.frameHeight
                )

            FrameItem(
                frameIndex = 0,
                bitmap = frameBitmap,
                fillRatio = 1f,
                startDuration = validatedDuration,
                endDuration = validatedDuration
            ).also {
                emitter.onNext(FramesResource.complete(arrayListOf(it)))
            }

            emitter.onComplete()
        }
    }

}