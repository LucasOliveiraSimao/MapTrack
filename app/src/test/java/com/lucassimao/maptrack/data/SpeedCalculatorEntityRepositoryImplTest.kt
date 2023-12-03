package com.lucassimao.maptrack.data

import com.lucassimao.maptrack.base.SpeedAverageBase.distanceInMeters
import com.lucassimao.maptrack.base.SpeedAverageBase.expected
import com.lucassimao.maptrack.base.SpeedAverageBase.totalTimeInMilliseconds
import com.lucassimao.maptrack.data.entity.SpeedCalculatorEntity
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SpeedCalculatorEntityRepositoryImplTest{
    @Test
    fun `calculate average speed`(){

        val mockSpeedCalculatorEntity = mockk<SpeedCalculatorEntity>()
        every { mockSpeedCalculatorEntity.calculateAverageSpeed(any(), any()) } returns expected

        val repository = SpeedCalculatorRepositoryImpl(mockSpeedCalculatorEntity)

        val result = repository.calculateAverageSpeed(distanceInMeters, totalTimeInMilliseconds)

        assertEquals(expected, result)
    }
}