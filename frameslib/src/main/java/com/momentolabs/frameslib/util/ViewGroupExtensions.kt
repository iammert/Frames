package com.momentolabs.frameslib.util

import android.view.View
import android.view.ViewGroup

inline fun ViewGroup.forEachIndexed(action: (Int, View) -> Unit) {
    for (i in 0 until childCount) {
        action(i, getChildAt(i))
    }
}