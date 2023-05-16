package com.lucassimao.maptrack.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class GetFormattedElapsedTimeTest {

    @Test
    fun `test with 1 hour, 23 minutes and 45 seconds`() {

        val totalTime = TimeUnit.HOURS.toMillis(1) + TimeUnit.MINUTES.toMillis(23) +
                TimeUnit.SECONDS.toMillis(45)

        val expected = "01:23:45"
        val actual = getFormattedElapsedTime(totalTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `test with 9 hour, 5 minutes and 2 seconds`() {

        val totalTime = TimeUnit.HOURS.toMillis(9) + TimeUnit.MINUTES.toMillis(5) +
                TimeUnit.SECONDS.toMillis(2)

        val expected = "09:05:02"
        val actual = getFormattedElapsedTime(totalTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `test with zero time`() {

        val totalTime = 0L

        val expected = "00:00:00"
        val actual = getFormattedElapsedTime(totalTime)

        assertEquals(expected, actual)
    }
}