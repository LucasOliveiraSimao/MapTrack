package com.lucassimao.maptrack.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_table")
data class RouteEntity(
    val image: Bitmap? = null,
    val currentDateInMillis: Long = 0L,
    val averageSpeed: Float = 0f,
    val distanceTraveledInKM: Float = 0f,
    val totalExecutionTime: Long = 0L
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
