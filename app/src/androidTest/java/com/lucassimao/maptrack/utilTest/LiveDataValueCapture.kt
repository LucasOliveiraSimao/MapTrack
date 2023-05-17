package com.lucassimao.maptrack.utilTest

import androidx.lifecycle.LiveData

fun <T> getValue(liveData: LiveData<T>): T {
    val observer = androidx.lifecycle.Observer<T> {}
    liveData.observeForever(observer)
    return liveData.value!!
}