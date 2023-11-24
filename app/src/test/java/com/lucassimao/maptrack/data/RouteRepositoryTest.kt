package com.lucassimao.maptrack.data

import com.lucassimao.maptrack.data.db.MapTrackDAO
import com.lucassimao.maptrack.base.Entity.routeEntity
import com.lucassimao.maptrack.base.Entity.routeList
import com.lucassimao.maptrack.base.Entity.totalDistance
import com.lucassimao.maptrack.base.Entity.totalTime
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RouteRepositoryTest {

    private val daoMock = mockk<MapTrackDAO>()
    private val repository = RouteRepository(daoMock)

    @Test
    fun testInsertRoute() = runBlocking {

        coEvery { daoMock.insertRoute(routeEntity) } returns Unit

        repository.insertRoute(routeEntity)

        coVerify(exactly = 1) { daoMock.insertRoute(routeEntity) }
    }

    @Test
    fun testGetAllRoutes() = runBlocking {

        every { daoMock.getAllRoutes() } returns routeList

        val result = repository.getAllRoutes()

        assertEquals(routeList, result)
    }

    @Test
    fun testGetTotalDistance() = runBlocking {

        coEvery { daoMock.getTotalDistance() } returns totalDistance

        val result = repository.getTotalDistance()

        assertEquals(totalDistance, result)
    }

    @Test
    fun testGetTotalExecutionTime() {

        every { daoMock.getTotalExecutionTime() } returns totalTime

        val result = repository.getTotalExecutionTime()

        assertEquals(totalTime, result)
    }

}