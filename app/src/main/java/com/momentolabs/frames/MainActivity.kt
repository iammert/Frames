package com.momentolabs.frames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.Status
import com.momentolabs.frameslib.ui.view.VideoFramesLayout
import com.momentolabs.frameslib.Frames
import com.momentolabs.frameslib.data.metadataprovider.ProviderType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val framesLayout = findViewById<VideoFramesLayout>(R.id.layoutFramesLayout)

        val frameRetrieveRequest = FrameRetrieveRequest.MultipleFrameRequest(
            videoPath = "/storage/emulated/0/Download/SampleVideo_1280x720_30mb.mp4",
            frameWidth = 400,
            frameHeight = 300,
            durationPerFrame = 1000
        )

        Frames
            .load(frameRetrieveRequest)
            .setProviderType(ProviderType.FFMPEG)
            .into { framesResource ->
                when (framesResource.status) {
                    Status.EMPTY_FRAMES -> Log.v("TEST", "emptyframes: ${framesResource.frames.size} ${System.currentTimeMillis()}")
                    Status.LOADING -> Log.v("TEST", "loading: ${framesResource.frames.size}")
                    Status.COMPLETED -> Log.v("TEST", "Completed: ${framesResource.frames.size} ${System.currentTimeMillis()}")
                }
            }

        Frames.load(frameRetrieveRequest).into(videoFramesLayout = framesLayout, orientation = LinearLayout.HORIZONTAL)
    }
}
