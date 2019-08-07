package com.momentolabs.videoframeslayoutlib

import android.widget.LinearLayout
import com.momentolabs.frameslib.data.VideoFrameRetriever
import com.momentolabs.frameslib.data.metadataprovider.ProviderType
import com.momentolabs.frameslib.data.model.FrameRetrieveRequest
import com.momentolabs.frameslib.data.model.FramesResource
import com.momentolabs.frameslib.ui.VideoFramesLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Frames private constructor(private val frameRetrieveRequest: FrameRetrieveRequest) {

    private var callback: ((FramesResource) -> Unit)? = null

    private var errorListener: ((Throwable) -> Unit)? = null

    private var videoFramesLayout: VideoFramesLayout? = null

    private var frameRetrieverDisposable: Disposable? = null

    private val videoFramesRetriever = VideoFrameRetriever(providerType = ProviderType.FFMPEG)

    fun setErrorListener(errorListener: (Throwable) -> Unit): Frames {
        this.errorListener = errorListener
        return this
    }

    fun into(callback: (FramesResource) -> Unit) {
        this.callback = callback
        loadFrames()
    }

    fun into(videoFramesLayout: VideoFramesLayout, orientation: Int = LinearLayout.HORIZONTAL) {
        this.videoFramesLayout = videoFramesLayout
        this.videoFramesLayout?.let {
            it.setFrameSize(
                frameRetrieveRequest.frameWidth.toFloat(),
                frameRetrieveRequest.frameHeight.toFloat()
            )
            it.setFramesOrientation(orientation)
        }

        loadFrames()
    }

    private fun loadFrames() {
        frameRetrieverDisposable = videoFramesRetriever
            .retrieveFrames(frameRetrieveRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { framesResource ->
                    callback?.let { it.invoke(framesResource) }
                    videoFramesLayout?.let { it.onFramesLoaded(framesResource) }
                },
                { error -> errorListener?.let { it.invoke(error) } })
    }

    private fun cancel() {
        frameRetrieverDisposable?.let {
            if (it.isDisposed.not()) {
                it.dispose()
            }
        }

        videoFramesLayout = null
        callback = null
        errorListener = null
    }

    companion object {

        fun load(frameRetrieveRequest: FrameRetrieveRequest) = Frames(frameRetrieveRequest)
    }
}