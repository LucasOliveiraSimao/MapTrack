package com.lucassimao.maptrack.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucassimao.maptrack.data.model.RouteEntity

@Dao
interface MapTrackDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: RouteEntity)

    @Query("SELECT * FROM route_table ORDER BY id DESC")
    fun getAllRoutes(): LiveData<List<RouteEntity>>

    @Query("SELECT SUM(distanceTraveledInKM) FROM route_table")
    fun getTotalDistance(): LiveData<Double>

    @Query("SELECT SUM(totalExecutionTime) FROM route_table")
    fun getTotalExecutionTime(): LiveData<Long>
}