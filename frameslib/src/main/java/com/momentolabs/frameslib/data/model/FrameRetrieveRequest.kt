package com.momentolabs.frameslib.data.model

data class FrameRetrieveRequest(
    val videoPath: String = "",
    val frameWidth: Int = 400,
    val frameHeight: Int = 300,
    val frameDuration: Long = 4000
)