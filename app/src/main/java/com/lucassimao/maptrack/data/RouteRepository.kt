package com.lucassimao.maptrack.data

import com.lucassimao.maptrack.data.db.MapTrackDAO
import javax.inject.Inject

class RouteRepository @Inject constructor(
    private val dao: MapTrackDAO
) {
    suspend fun insertRoute(route: RouteEntity) = dao.insertRoute(route)

    fun getAllRoutes() = dao.getAllRoutes()

    fun getTotalDistance() = dao.getTotalDistance()

    fun getTotalExecutionTime() = dao.getTotalExecutionTime()
}