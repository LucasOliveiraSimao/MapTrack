package com.lucassimao.maptrack.data.entity

import com.lucassimao.maptrack.base.SpeedAverageBase.distanceInMeters
import com.lucassimao.maptrack.base.SpeedAverageBase.expected
import com.lucassimao.maptrack.base.SpeedAverageBase.totalTimeInMilliseconds
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SpeedCalculatorEntityTest {
    @Test
    fun `calculate average speed`() {

        val mockConverterMetersToKilometers = mockk<(Float) -> Float>()
        val mockConverterMillisToHours = mockk<(Long) -> Float>()

        every { mockConverterMetersToKilometers.invoke(any()) } returns expected
        every { mockConverterMillisToHours.invoke(any()) } returns 1.0f

        val result = SpeedCalculatorEntity().calculateAverageSpeed(
            distanceInMeters,
            totalTimeInMilliseconds
        )

        assertEquals(expected, result)
    }
}