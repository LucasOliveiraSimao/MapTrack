package com.lucassimao.maptrack.util

import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.util.Constants.ROUTE_LINE_COLOR
import com.lucassimao.maptrack.util.Constants.ROUTE_LINE_WIDTH
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BuildPolylineUtilTest {

    @Test
    fun testBuildPolylineOption() {
        val preLastPosition = LatLng(10.0, 20.0)
        val lastPosition = LatLng(30.0, 40.0)

        val polylineOptions = buildPolylineOption(preLastPosition, lastPosition)

        assertEquals(ROUTE_LINE_COLOR, polylineOptions.color)

        assertEquals(ROUTE_LINE_WIDTH, polylineOptions.width)

        val points = polylineOptions.points
        assertEquals(2, points.size)
        assertEquals(preLastPosition, points[0])
        assertEquals(lastPosition, points[1])
    }

    @Test
    fun testBuildPolylineOption2() {
        val path = listOf(
            LatLng(10.0, 20.0),
            LatLng(30.0, 40.0),
            LatLng(50.0, 60.0)
        )

        val polylineOptions = buildPolylineOption(path)

        assertEquals(ROUTE_LINE_COLOR, polylineOptions.color)

        assertEquals(ROUTE_LINE_WIDTH, polylineOptions.width)

        val points = polylineOptions.points
        assertEquals(3, points.size)
        assertEquals(path[0], points[0])
        assertEquals(path[1], points[1])
        assertEquals(path[2], points[2])
    }
}