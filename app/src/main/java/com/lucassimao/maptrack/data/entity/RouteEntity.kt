package com.lucassimao.maptrack.data.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "route_table")
@Parcelize
data class RouteEntity(
    val image: Bitmap? = null,
    val currentDateInMillis: Long = 0L,
    val averageSpeed: Float = 0f,
    val distanceTraveledInKM: Double = 0.0,
    val totalExecutionTime: Long = 0L
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}