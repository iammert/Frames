package com.momentolabs.frameslib.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.momentolabs.frameslib.R
import com.momentolabs.frameslib.data.model.FramesResource
import com.momentolabs.frameslib.data.model.Status.*
import com.momentolabs.frameslib.util.forEachIndexed

class VideoFramesLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var frameWidth: Float = context.resources.getDimension(R.dimen.frame_width)

    private var frameHeight: Float = context.resources.getDimension(R.dimen.frame_height)

    private var layoutOrientation: Int = LinearLayout.HORIZONTAL

    private var framesResource: FramesResource? = null

    private val videoFramesLinearLayout = LinearLayout(context)
        .also { it.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT) }
        .also { it.orientation = layoutOrientation }

    init {
        addView(videoFramesLinearLayout)
    }

    fun setFrameSize(frameWidth: Float, frameHeight: Float) {
        this.frameWidth = frameWidth
        this.frameHeight = frameHeight
    }

    fun setFramesOrientation(@LinearLayoutCompat.OrientationMode orientation: Int) {
        layoutOrientation = orientation
        videoFramesLinearLayout.orientation = layoutOrientation
        videoFramesLinearLayout.postInvalidate()
    }

    fun getFramesTotalWidth(): Float {
        return framesResource?.let {
            var totalWidth = 0f

            it.frames.forEach {
                totalWidth += frameWidth * it.fillRatio
            }

            return totalWidth
        } ?: 0f
    }

    fun onFramesLoaded(framesResource: FramesResource) {
        this.framesResource = framesResource
        when (framesResource.status) {
            EMPTY_FRAMES -> initializeEmptyFrames(framesResource = framesResource)
            LOADING -> loadAvailableFrames(framesResource = framesResource)
            COMPLETED -> completeFrameLoading()
        }
    }

    private fun initializeEmptyFrames(framesResource: FramesResource) {
        videoFramesLinearLayout.removeAllViews()

        framesResource.frames.forEach {
            FrameImageView(context)
                .apply {
                    this.layoutParams = LayoutParams((frameWidth * it.fillRatio).toInt(), frameHeight.toInt())
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                .also {
                    videoFramesLinearLayout.addView(it)
                }
        }

        videoFramesLinearLayout.requestLayout()
        videoFramesLinearLayout.postInvalidate()
    }

    private fun loadAvailableFrames(framesResource: FramesResource) {
        videoFramesLinearLayout.forEachIndexed { viewIndex, view ->
            if (view is FrameImageView) {
                val frameItem = framesResource.getFrameItem(viewIndex)

                if (view.drawable == null && frameItem.bitmap != null) {
                    view.setImageBitmap(frameItem.bitmap)
                    view.postInvalidate()
                }
            }
        }
    }

    private fun completeFrameLoading() {
        //no-op
    }
}