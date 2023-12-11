package com.lucassimao.maptrack.domain.usecase

import com.lucassimao.maptrack.domain.repository.SpeedCalculatorRepository
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