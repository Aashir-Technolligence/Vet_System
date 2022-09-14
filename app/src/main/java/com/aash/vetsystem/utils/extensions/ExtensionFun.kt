package com.aash.vetsystem.utils

import android.view.View

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}
fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}