package com.lucassimao.maptrack.data.entity

import com.lucassimao.maptrack.data.core.ConversionFactor.MILLISECONDS_TO_SECONDS_CONVERSION_FACTOR
import com.lucassimao.maptrack.data.core.ConversionFactor.MINUTES_TO_HOURS_CONVERSION_FACTOR
import com.lucassimao.maptrack.data.core.ConversionFactor.SECONDS_TO_MINUTES_CONVERSION_FACTOR
import javax.inject.Inject

class SpeedCalculatorEntity @Inject constructor() {

    fun calculateAverageSpeed(distanceInMeters: Float, totalTimeInMilliseconds: Long): Float {
        val millisToHours = converterMillisToHours(totalTimeInMilliseconds)
        return calculateAverageSpeed(distanceInMeters, millisToHours)
    }

    private fun converterMillisToHours(totalTimeInMilliseconds: Long): Float {
        return totalTimeInMilliseconds / (
                MILLISECONDS_TO_SECONDS_CONVERSION_FACTOR *
                        SECONDS_TO_MINUTES_CONVERSION_FACTOR *
                        MINUTES_TO_HOURS_CONVERSION_FACTOR)
    }

    private fun calculateAverageSpeed(metersToKilometers: Float, millisToHours: Float): Float {
        return metersToKilometers / millisToHours
    }
}
