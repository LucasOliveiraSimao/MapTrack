package com.lucassimao.maptrack.data.entity

import com.lucassimao.maptrack.data.core.ConversionFactor.METERS_TO_KILOMETERS_CONVERSION_FACTOR
import javax.inject.Inject

class MetersToKilometersConverterEntity @Inject constructor() {
    fun metersToKilometers(distanceInMeters: Float): Float {
        return distanceInMeters / METERS_TO_KILOMETERS_CONVERSION_FACTOR
    }
}
