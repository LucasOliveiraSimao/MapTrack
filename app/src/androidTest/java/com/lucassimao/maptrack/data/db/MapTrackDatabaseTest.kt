package com.lucassimao.maptrack.data.db

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lucassimao.maptrack.data.entity.RouteEntity
import com.lucassimao.maptrack.base.convertToBitmap
import com.lucassimao.maptrack.base.getDrawable
import com.lucassimao.maptrack.base.getValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapTrackDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MapTrackDatabase
    private lateinit var dao: MapTrackDAO

    private val drawable = getDrawable()
    private val bitmap = convertToBitmap(drawable)

    private val image: Bitmap? = bitmap
    private val currentDateInMillis = 0L
    private val averageSpeed = 0f
    private val distanceTraveledInKM = 0.0
    private val totalExecutionTime = 0L

    private val routeList = listOf(
        RouteEntity(
            image,
            currentDateInMillis,
            averageSpeed,
            distanceTraveledInKM = 10.0,
            totalExecutionTime = 100L
        ),
        RouteEntity(
            image,
            currentDateInMillis,
            averageSpeed,
            distanceTraveledInKM = 15.0,
            totalExecutionTime = 200L
        ),
        RouteEntity(
            image,
            currentDateInMillis,
            averageSpeed,
            distanceTraveledInKM = 5.0,
            totalExecutionTime = 50L
        )
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MapTrackDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.mapTrackDAO()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun testInsertRoute(): Unit = runBlocking {

        val routeEntity = RouteEntity(
            image,
            currentDateInMillis,
            averageSpeed,
            distanceTraveledInKM,
            totalExecutionTime
        )

        dao.insertRoute(routeEntity)

        val allRoutes = getValue(dao.getAllRoutes())
        assert(allRoutes.isNotEmpty())
    }

    @Test
    fun testGetAllRoutes(): Unit = runBlocking {

        routeList.forEach { dao.insertRoute(it) }

        val allRoutes = getValue(dao.getAllRoutes())
        assert(allRoutes.size == routeList.size)
    }

    @Test
    fun testGetTotalDistance(): Unit = runBlocking {

        routeList.forEach { dao.insertRoute(it) }

        val totalDistance = getValue(dao.getTotalDistance())
        val expectedTotalDistance = routeList.sumOf { it.distanceTraveledInKM }
        assert(totalDistance == expectedTotalDistance)
    }

    @Test
    fun testGetTotalExecutionTime(): Unit = runBlocking {

        routeList.forEach { dao.insertRoute(it) }

        val totalExecutionTime = getValue(dao.getTotalExecutionTime())
        val expectedTotalExecutionTime = routeList.sumOf { it.totalExecutionTime }
        assert(totalExecutionTime == expectedTotalExecutionTime)
    }

}