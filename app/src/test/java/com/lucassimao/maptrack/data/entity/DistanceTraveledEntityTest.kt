package com.lucassimao.maptrack.data.entity

import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.data.core.ListOfLocations
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DistanceTraveledEntityTest {
    @Test
    fun `calculate total distance traveled`() {
        val mockConverter = mockk<MetersToKilometersConverterEntity>()
        val distanceTraveledEntity = DistanceTraveledEntity(mockConverter)
        val expected = 0.5f

        every { mockConverter.metersToKilometers(any()) } returns expected

        val routePolylines: List<ListOfLocations> = listOf(
            mutableListOf(LatLng(0.0, 0.0), LatLng(1.0, 1.0)),
            mutableListOf(LatLng(1.0, 1.0), LatLng(2.0, 2.0))
        )

        val result = distanceTraveledEntity.calculateTotalDistanceTraveled(routePolylines)

        assertEquals(expected, result)
    }
}