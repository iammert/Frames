package com.momentolabs.frameslib.data.model

sealed class FrameRetrieveRequest(
    open var videoPath: String,
    open var frameWidth: Int,
    open var frameHeight: Int
) {

    data class MultipleFrameRequest(
        override var videoPath: String = "",
        override var frameWidth: Int = FRAME_WIDTH,
        override var frameHeight: Int = FRAME_HEIGHT,
        val durationPerFrame: Long = 4000
    ) : FrameRetrieveRequest(videoPath, frameWidth, frameHeight)

    data class RangeFrameRequest(
        override var videoPath: String = "",
        override var frameWidth: Int = FRAME_WIDTH,
        override var frameHeight: Int = FRAME_HEIGHT,
        val durationPerFrame: Long = 4000,
        val startDuration: Long,
        val endDuration: Long
    ) : FrameRetrieveRequest(videoPath, frameWidth, frameHeight)

    data class SingleFrameRequest(
        override var videoPath: String = "",
        override var frameWidth: Int = FRAME_WIDTH,
        override var frameHeight: Int = FRAME_HEIGHT,
        val durationInMillis: Long = 0
    ) : FrameRetrieveRequest(videoPath, frameWidth, frameHeight)

    companion object {
        private const val FRAME_WIDTH: Int = 400
        private const val FRAME_HEIGHT: Int = 300

    }
}