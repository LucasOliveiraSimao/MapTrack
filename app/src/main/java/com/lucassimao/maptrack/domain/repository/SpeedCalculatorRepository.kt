package com.lucassimao.maptrack.domain.repository

interface SpeedCalculatorRepository {
    fun calculateAverageSpeed(distanceInMeters: Float, totalTimeInMilliseconds: Long): Float
}