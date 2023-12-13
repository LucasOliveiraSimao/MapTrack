package com.lucassimao.maptrack.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment

fun Fragment.isMobileDataEnable(): Boolean {
    val connectivityManager = requireContext()
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}