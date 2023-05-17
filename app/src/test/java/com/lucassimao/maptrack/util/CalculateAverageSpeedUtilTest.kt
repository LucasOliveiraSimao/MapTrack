package com.lucassimao.maptrack.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateAverageSpeedUtilTest {
    @Test
    fun testCalculateAverageSpeed() {
        val metersToKilometers = 5f
        val millisToHours = 1f

        val expected = metersToKilometers / millisToHours

        val averageSpeed = calculateAverageSpeed(metersToKilometers, millisToHours)

        assertEquals(expected, averageSpeed)
    }
}