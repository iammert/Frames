package com.momentolabs.frameslib.data.frameloader

import com.momentolabs.frameslib.data.metadataprovider.VideoMetaDataProvider
import com.momentolabs.frameslib.data.model.FrameItem
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import io.reactivex.Observable

class MultipleFrameLoader(private val videoMetaDataProvider: VideoMetaDataProvider) :
    FrameLoader<FrameRetrieveRequest.MultipleFrameRequest> {

    override fun loadFrames(frameRetrieveRequest: FrameRetrieveRequest.MultipleFrameRequest): Observable<FramesResource> {

        return Observable.create { emitter ->

            val initialFrames = createEmptyFrames(
                frameRetrieveRequest = frameRetrieveRequest,
                videoMetaDataProvider = videoMetaDataProvider
            )

            emitter.onNext(FramesResource.initialize(initialFrames))

            initialFrames.forEach { emptyFrameItem ->
                val frameBitmap = videoMetaDataProvider
                    .getFrameAt(
                        frameInMillis = emptyFrameItem.startDuration,
                        width = frameRetrieveRequest.frameWidth,
                        height = frameRetrieveRequest.frameHeight
                    )

                FrameItem(
                    frameIndex = emptyFrameItem.frameIndex,
                    bitmap = frameBitmap,
                    fillRatio = emptyFrameItem.fillRatio,
                    startDuration = emptyFrameItem.startDuration,
                    endDuration = emptyFrameItem.endDuration
                ).also {
                    initialFrames[it.frameIndex] = it
                    emitter.onNext(FramesResource.loading(initialFrames))
                }
            }

            emitter.onNext(FramesResource.complete(reloadEmptyBitmapsWithClosest(initialFrames)))
            emitter.onComplete()
        }
    }


    private fun createEmptyFrames(
        frameRetrieveRequest: FrameRetrieveRequest.MultipleFrameRequest,
        videoMetaDataProvider: VideoMetaDataProvider
    ): ArrayList<FrameItem> {
        val frameItems = arrayListOf<FrameItem>()
        val totalFrameCount = calculateFrameCount(frameRetrieveRequest, videoMetaDataProvider.getVideoDuration())
        val totalVideoDuration = videoMetaDataProvider.getVideoDuration()
        var currentTimeTemp: Long = 0
        for (index in 0 until totalFrameCount) {
            val timeLeft = totalVideoDuration - currentTimeTemp

            if (isLeftTimeLowerThanFrameDuration(timeLeft, frameRetrieveRequest.durationPerFrame)) {
                val ratio = timeLeft.toFloat() / frameRetrieveRequest.durationPerFrame.toFloat()
                val frameEndDuration = currentTimeTemp + timeLeft
                frameItems.add(
                    FrameItem(
                        frameIndex = index,
                        fillRatio = ratio,
                        startDuration = currentTimeTemp,
                        endDuration = frameEndDuration
                    )
                )
            } else {
                frameItems.add(
                    FrameItem(
                        frameIndex = index,
                        fillRatio = 1.0F,
                        startDuration = currentTimeTemp,
                        endDuration = currentTimeTemp + frameRetrieveRequest.durationPerFrame
                    )
                )
            }

            currentTimeTemp += frameRetrieveRequest.durationPerFrame
        }
        return frameItems
    }

    private fun calculateFrameCount(
        frameRetrieveRequest: FrameRetrieveRequest.MultipleFrameRequest,
        videoDuration: Long
    ): Int {
        return Math.ceil((videoDuration.toDouble() / frameRetrieveRequest.durationPerFrame)).toInt()
    }

    private fun isLeftTimeLowerThanFrameDuration(leftTime: Long, frameDuration: Long): Boolean {
        return leftTime < frameDuration
    }

    private fun reloadEmptyBitmapsWithClosest(frameList: ArrayList<FrameItem>): List<FrameItem> {
        for (i in 0 until frameList.size) {
            val frameItem = frameList[i]

            if (frameItem.bitmap == null) {
                val frameItemIndex = frameItem.frameIndex

                for (leftSearchIndex in frameItemIndex downTo 0) {
                    if (frameList[leftSearchIndex].bitmap != null) {
                        val updatedFrameItem = FrameItem(
                            frameIndex = frameItem.frameIndex,
                            bitmap = frameList[leftSearchIndex].bitmap,
                            fillRatio = frameItem.fillRatio,
                            startDuration = frameItem.startDuration,
                            endDuration = frameItem.endDuration
                        )
                        frameList[i] = updatedFrameItem
                        break
                    }
                }
            }
        }

        return frameList
    }

}