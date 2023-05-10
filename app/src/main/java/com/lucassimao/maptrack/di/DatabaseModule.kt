package com.lucassimao.maptrack.di

import android.content.Context
import androidx.room.Room
import com.lucassimao.maptrack.data.db.MapTrackDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MapTrackDatabase::class.java,
        "map_track_database"
    ).build()

    @Singleton
    @Provides
    fun provideDAO(db: MapTrackDatabase) = db.mapTrackDAO()
}