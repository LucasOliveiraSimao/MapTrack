package com.lucassimao.maptrack.domain

interface SpeedCalculatorRepository {
    fun calculateAverageSpeed(distanceInMeters: Float, totalTimeInMilliseconds: Long): Float
}