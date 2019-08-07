package com.momentolabs.frameslib.util

fun <T> List<T>.isLastItem(item: T): Boolean {
    return indexOf(item) == size - 1 && size > 0
}