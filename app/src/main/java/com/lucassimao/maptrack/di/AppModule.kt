package com.lucassimao.maptrack.di

import com.lucassimao.maptrack.data.RouteRepository
import com.lucassimao.maptrack.data.db.MapTrackDAO
import com.lucassimao.maptrack.ui.home.RouteViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(dao: MapTrackDAO) = RouteRepository(dao)

    @Provides
    fun provideViewModel(repository: RouteRepository) = RouteViewModel(repository)
}