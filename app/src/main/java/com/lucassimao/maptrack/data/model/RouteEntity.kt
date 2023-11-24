package com.lucassimao.maptrack.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_table")
data class RouteEntity(
    val image: Bitmap? = null,
    val currentDateInMillis: Long = 0L,
    val averageSpeed: Float = 0f,
    val distanceTraveledInKM: Double = 0.0,
    val totalExecutionTime: Long = 0L
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
