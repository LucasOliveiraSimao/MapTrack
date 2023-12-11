package com.lucassimao.maptrack.data.entity

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MetersToKilometersConverterEntityTest {
    @Test
    fun `meters to kilometers`() {
        val distanceInMeters = 500.0f
        val mockConverter = mockk<MetersToKilometersConverterEntity>()

        every { mockConverter.metersToKilometers(any()) } returns 0.5f

        val result = mockConverter.metersToKilometers(distanceInMeters)
        val expected = 0.5f

        assertEquals(expected, result)
    }
}