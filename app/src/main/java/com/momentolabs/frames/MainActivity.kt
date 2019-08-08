package com.momentolabs.frames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.Status
import com.momentolabs.frameslib.ui.view.VideoFramesLayout
import com.momentolabs.frameslib.Frames

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val framesLayout = findViewById<VideoFramesLayout>(R.id.layoutFramesLayout)

        val frameRetrieveRequest = FrameRetrieveRequest.RangeFrameRequest(
            videoPath = "/storage/emulated/0/Download/SampleVideo_1280x720_30mb.mp4",
            frameWidth = 400,
            frameHeight = 300,
            durationPerFrame = 4000,
            startDuration = 8000,
            endDuration = 11100
        )

        Frames
            .load(frameRetrieveRequest)
            .into { framesResource ->
                when (framesResource.status) {
                    Status.EMPTY_FRAMES -> Log.v("TEST", "emptyframes: ${framesResource.frames.size}")
                    Status.LOADING -> Log.v("TEST", "loading: ${framesResource.frames.size}")
                    Status.COMPLETED -> Log.v("TEST", "Completed: ${framesResource.frames.size}")
                }
            }

        Frames.load(frameRetrieveRequest).into(videoFramesLayout = framesLayout, orientation = LinearLayout.HORIZONTAL)
    }
}
