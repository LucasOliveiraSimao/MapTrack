package com.lucassimao.maptrack.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucassimao.maptrack.data.RouteRepository
import com.lucassimao.maptrack.ui.home.RouteViewModel
import com.lucassimao.maptrack.base.Entity.routeEntity
import com.lucassimao.maptrack.base.Entity.routeList
import com.lucassimao.maptrack.base.Entity.totalDistance
import com.lucassimao.maptrack.base.Entity.totalTime
import com.lucassimao.maptrack.base.MainCoroutineScopeRule
import com.lucassimao.maptrack.base.getValueForTest
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class RouteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    private val repositoryMock = mockk<RouteRepository>(relaxed = true)
    private val viewModel = RouteViewModel(repositoryMock)


    @Test
    fun testInsertRoute() = runBlocking {

        viewModel.insertRoute(routeEntity)

        coVerify(exactly = 1) { repositoryMock.insertRoute(routeEntity) }
    }

    @Test
    fun testGetAllRoutes() {

        every { repositoryMock.getAllRoutes() } returns routeList

        assertEquals(routeList.getValueForTest(), viewModel.getAllRoutes.getValueForTest())

    }

    @Test
    fun testGetTotalDistance() {

        every { repositoryMock.getTotalDistance() } returns totalDistance

        assertEquals(totalDistance.getValueForTest(), viewModel.getTotalDistance.getValueForTest())
    }

    @Test
    fun testGetTotalExecutionTime() {

        every { repositoryMock.getTotalExecutionTime() } returns totalTime

        assertEquals(totalTime.getValueForTest(), viewModel.getTotalExecutionTime.getValueForTest())

    }
}