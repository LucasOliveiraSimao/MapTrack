package com.lucassimao.maptrack.base

import androidx.lifecycle.LiveData
import java.util.concurrent.atomic.AtomicReference

class TestLiveData<T>(initialValue: T) : LiveData<T>() {
    private val data = AtomicReference<T>(initialValue)

    override fun getValue(): T? {
        return data.get()
    }

    override fun setValue(value: T?) {
        data.set(value)
        postValue(value)
    }
}