package com.lucassimao.maptrack.base

import android.graphics.Bitmap
import com.lucassimao.maptrack.data.entity.RouteEntity
import io.mockk.mockk

object Entity {
    val totalDistance = TestLiveData(100.00)
    val totalTime = TestLiveData(100L)

    private val image: Bitmap = mockk()
    private const val currentDateInMillis = 0L
    private const val averageSpeed = 0f
    private const val distanceTraveledInKM = 0.0
    private const val totalExecutionTime = 0L

    val routeEntity = RouteEntity(
        image,
        currentDateInMillis,
        averageSpeed,
        distanceTraveledInKM,
        totalExecutionTime
    )

    val routeList = TestLiveData(
        listOf(
            RouteEntity(
                image,
                currentDateInMillis,
                averageSpeed,
                distanceTraveledInKM = 10.0,
                totalExecutionTime = 100L
            ),
            RouteEntity(
                image,
                currentDateInMillis,
                averageSpeed,
                distanceTraveledInKM = 15.0,
                totalExecutionTime = 200L
            ),
            RouteEntity(
                image,
                currentDateInMillis,
                averageSpeed,
                distanceTraveledInKM = 5.0,
                totalExecutionTime = 50L
            )
        )
    )
}