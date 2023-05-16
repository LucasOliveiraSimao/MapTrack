package com.lucassimao.maptrack.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class MillisToHoursTest {

    @Test
    fun `test with 3600000 milliseconds (1 hour)`() {

        val totalTime = 3600000L

        val expected = 1.0f
        val actual = millisToHours(totalTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `test with 5400000 milliseconds (1,5 hour)`() {

        val totalTime = 5400000L

        val expected = 1.5f
        val actual = millisToHours(totalTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `test with zero time`() {

        val totalTime = 0L

        val expected = 0.0f
        val actual = millisToHours(totalTime)

        assertEquals(expected, actual)
    }
}