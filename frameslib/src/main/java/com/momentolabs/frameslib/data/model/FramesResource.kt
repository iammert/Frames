package com.momentolabs.frameslib.data.model

import java.lang.IllegalArgumentException

data class FramesResource(val status: Status, val frames: List<FrameItem>) {

    fun getFrameItem(index: Int): FrameItem {
        if (index in 0..frames.size) {
            return frames[index]
        }
        throw IllegalArgumentException("Requested index is $index but frame list size is ${frames.size}")
    }

    companion object {
        fun initialize(frames: List<FrameItem>) =
            FramesResource(
                Status.EMPTY_FRAMES,
                frames
            )

        fun loading(frames: List<FrameItem>) =
            FramesResource(
                Status.LOADING,
                frames
            )

        fun complete(frames: List<FrameItem>) =
            FramesResource(
                Status.COMPLETED,
                frames
            )
    }
}