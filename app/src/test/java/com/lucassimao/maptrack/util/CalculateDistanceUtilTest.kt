package com.lucassimao.maptrack.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateDistanceUtilTest{

    @Test
    fun testMetersToKilometers() {
        val distanceInMeters = 1000.0f

        val distanceInKilometers = metersToKilometers(distanceInMeters)

        assertEquals(1.0f, distanceInKilometers)
    }

    @Test
    fun testFormatFloatToTwoDecimalPlaces() {
        val value = 10.123456f

        val formattedValue = formatFloatToTwoDecimalPlaces(value)

        assertEquals("10,12", formattedValue)
    }
}