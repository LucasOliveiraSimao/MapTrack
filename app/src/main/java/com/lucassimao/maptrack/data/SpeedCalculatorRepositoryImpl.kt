package com.lucassimao.maptrack.data

import com.lucassimao.maptrack.data.entity.SpeedCalculatorEntity
import com.lucassimao.maptrack.domain.SpeedCalculatorRepository
import javax.inject.Inject

class SpeedCalculatorRepositoryImpl @Inject constructor(
    private val speedCalculatorEntity: SpeedCalculatorEntity
) : SpeedCalculatorRepository {
    override fun calculateAverageSpeed(
        distanceInMeters: Float,
        totalTimeInMilliseconds: Long
    ): Float {
        return speedCalculatorEntity.calculateAverageSpeed(
            distanceInMeters,
            totalTimeInMilliseconds
        )
    }
}