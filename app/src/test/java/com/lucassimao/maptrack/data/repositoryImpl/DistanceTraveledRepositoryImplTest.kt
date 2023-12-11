package com.lucassimao.maptrack.data.repositoryImpl

import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.data.core.ListOfLocations
import com.lucassimao.maptrack.data.entity.DistanceTraveledEntity
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DistanceTraveledRepositoryImplTest {
    @Test
    fun `calculate total distance traveled`() {
        val mockDistanceTraveledEntity = mockk<DistanceTraveledEntity>()
        val distanceTraveledRepository = DistanceTraveledRepositoryImpl(mockDistanceTraveledEntity)

        val routePolylines: List<ListOfLocations> = listOf(
            mutableListOf(LatLng(0.0, 0.0), LatLng(1.0, 1.0)),
            mutableListOf(LatLng(1.0, 1.0), LatLng(2.0, 2.0))
        )

        val expectedDistance = 10.0f
        every { mockDistanceTraveledEntity.calculateTotalDistanceTraveled(routePolylines) } returns expectedDistance

        val result = distanceTraveledRepository.calculateTotalDistanceTraveled(routePolylines)

        assertEquals(expectedDistance, result)
    }
}