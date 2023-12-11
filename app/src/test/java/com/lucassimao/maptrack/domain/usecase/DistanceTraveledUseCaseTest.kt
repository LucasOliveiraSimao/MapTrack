package com.lucassimao.maptrack.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.data.core.ListOfLocations
import com.lucassimao.maptrack.domain.repository.DistanceTraveledRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DistanceTraveledUseCaseTest {
    @Test
    fun `calculate total distance traveled`() {
        val mockDistanceTraveledRepository = mockk<DistanceTraveledRepository>()
        val distanceTraveledUseCase = DistanceTraveledUseCase(mockDistanceTraveledRepository)

        val routePolylines: List<ListOfLocations> = listOf(
            mutableListOf(LatLng(0.0, 0.0), LatLng(1.0, 1.0)),
            mutableListOf(LatLng(1.0, 1.0), LatLng(2.0, 2.0))
        )

        val expectedDistance = 10.0f
        every { mockDistanceTraveledRepository.calculateTotalDistanceTraveled(routePolylines) } returns expectedDistance

        val result = distanceTraveledUseCase.execute(routePolylines)

        assertEquals(expectedDistance, result)
    }
}