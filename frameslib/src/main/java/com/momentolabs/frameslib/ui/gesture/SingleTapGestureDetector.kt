package com.lyrebirdstudio.videoeditor.lib.arch.library.ui.draggablelayout.gesture

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent

class SingleTapGestureDetector(val context: Context, event: (motionEvent: MotionEvent) -> Unit) {

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            event.invoke(e!!)
            return true
        }
    })

    fun onTouchEvent(motionEvent: MotionEvent) = gestureDetector.onTouchEvent(motionEvent)
}