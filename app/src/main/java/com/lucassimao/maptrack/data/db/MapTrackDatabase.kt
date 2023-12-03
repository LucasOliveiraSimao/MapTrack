package com.lucassimao.maptrack.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucassimao.maptrack.data.entity.RouteEntity
import com.lucassimao.maptrack.util.ConverterBitmapUtil

@Database(
    entities = [RouteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterBitmapUtil::class)
abstract class MapTrackDatabase : RoomDatabase() {
    abstract fun mapTrackDAO(): MapTrackDAO
}