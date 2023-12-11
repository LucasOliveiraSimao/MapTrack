package com.lucassimao.maptrack.data.repositoryImpl

import com.lucassimao.maptrack.data.core.ListOfLocations
import com.lucassimao.maptrack.data.entity.DistanceTraveledEntity
import com.lucassimao.maptrack.domain.repository.DistanceTraveledRepository
import javax.inject.Inject

class DistanceTraveledRepositoryImpl @Inject constructor(
    private val distanceTraveledEntity: DistanceTraveledEntity
) : DistanceTraveledRepository {
    override fun calculateTotalDistanceTraveled(routePolylines: List<ListOfLocations>): Float {
        return distanceTraveledEntity.calculateTotalDistanceTraveled(routePolylines)
    }
}