package com.lucassimao.maptrack.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucassimao.maptrack.base.MainCoroutineScopeRule
import com.lucassimao.maptrack.base.SpeedAverageBase.distanceInMeters
import com.lucassimao.maptrack.base.SpeedAverageBase.expected
import com.lucassimao.maptrack.base.SpeedAverageBase.totalTimeInMilliseconds
import com.lucassimao.maptrack.base.getValueForTest
import com.lucassimao.maptrack.domain.SpeedAverageUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

class SpeedAverageViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    private val useCase = mockk<SpeedAverageUseCase>(relaxed = true)
    private val viewModel = SpeedAverageViewModel(useCase)

    @Test
    fun `test get speed average`() {

        every { useCase.execute(distanceInMeters, totalTimeInMilliseconds) } returns expected

        assertEquals(
            expected,
            viewModel.getSpeedAverage(distanceInMeters, totalTimeInMilliseconds).getValueForTest()
        )
    }
}