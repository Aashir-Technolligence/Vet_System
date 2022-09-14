package com.aash.vetsystem.utils.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkConnected(): Boolean {
    val nc =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return nc.activeNetworkInfo !=null && nc.activeNetworkInfo!!.isConnected
}