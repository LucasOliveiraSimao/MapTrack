package com.lucassimao.maptrack.ui.map.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.base.MainCoroutineScopeRule
import com.lucassimao.maptrack.base.getValueForTest
import com.lucassimao.maptrack.data.core.ListOfLocations
import com.lucassimao.maptrack.domain.usecase.DistanceTraveledUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DistanceTraveledViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    private lateinit var viewModel: DistanceTraveledViewModel
    private val mockUseCase = mockk<DistanceTraveledUseCase>()

    @Before
    fun setUp() {
        viewModel = DistanceTraveledViewModel(mockUseCase)
    }

    @Test
    fun `getDistanceTraveled - updates distanceTraveled LiveData`() {
        val routePolylines: List<ListOfLocations> = listOf(
            mutableListOf(LatLng(0.0, 0.0), LatLng(1.0, 1.0)),
            mutableListOf(LatLng(1.0, 1.0), LatLng(2.0, 2.0))
        )

        val expectedDistance = 10.0f // Replace with your expected result
        every { mockUseCase.execute(routePolylines) } returns expectedDistance

        assertNotNull(viewModel.getDistanceTraveled(routePolylines).getValueForTest())
        assertEquals(expectedDistance, viewModel.getDistanceTraveled(routePolylines).getValueForTest())

    }
}