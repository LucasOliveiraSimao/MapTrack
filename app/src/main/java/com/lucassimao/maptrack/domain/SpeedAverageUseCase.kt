package com.lucassimao.maptrack.domain

import javax.inject.Inject

class SpeedAverageUseCase @Inject constructor(
    private val speedCalculatorRepository: SpeedCalculatorRepository
) {
    fun execute(distanceInMeters: Float, totalTimeInMilliseconds: Long): Float {
        return speedCalculatorRepository.calculateAverageSpeed(
            distanceInMeters,
            totalTimeInMilliseconds
        )
    }
}