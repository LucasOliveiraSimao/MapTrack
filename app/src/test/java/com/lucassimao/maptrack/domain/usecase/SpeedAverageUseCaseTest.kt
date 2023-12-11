package com.lucassimao.maptrack.domain.usecase

import com.lucassimao.maptrack.base.SpeedAverageBase.distanceInMeters
import com.lucassimao.maptrack.base.SpeedAverageBase.expected
import com.lucassimao.maptrack.base.SpeedAverageBase.totalTimeInMilliseconds
import com.lucassimao.maptrack.domain.repository.SpeedCalculatorRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SpeedAverageUseCaseTest {
    @Test
    fun `test execute`() {

        val mockRepository = mockk<SpeedCalculatorRepository>()

        every {
            mockRepository.calculateAverageSpeed(
                distanceInMeters,
                totalTimeInMilliseconds
            )
        } returns expected

        val useCase = SpeedAverageUseCase(mockRepository)

        val result = useCase.execute(distanceInMeters, totalTimeInMilliseconds)

        assertEquals(expected, result)
    }
}