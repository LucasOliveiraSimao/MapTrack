package com.lucassimao.maptrack.base

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.lucassimao.maptrack.R

fun getDrawable(): Drawable? {
    return ContextCompat.getDrawable(
        ApplicationProvider.getApplicationContext(),
        R.drawable.bitmap_test
    )
}

fun convertToBitmap(drawable: Drawable?): Bitmap? {
    val bitmap = drawable.let {
        Bitmap.createBitmap(
            it!!.intrinsicWidth,
            it.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }
    return bitmap
}