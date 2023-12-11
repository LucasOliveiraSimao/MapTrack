package com.lucassimao.maptrack.data.di

import com.lucassimao.maptrack.data.entity.DistanceTraveledEntity
import com.lucassimao.maptrack.data.entity.MetersToKilometersConverterEntity
import com.lucassimao.maptrack.data.entity.SpeedCalculatorEntity
import com.lucassimao.maptrack.data.repositoryImpl.DistanceTraveledRepositoryImpl
import com.lucassimao.maptrack.data.repositoryImpl.SpeedCalculatorRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideSpeedAverageRepository(speedCalculatorEntity: SpeedCalculatorEntity) =
        SpeedCalculatorRepositoryImpl(speedCalculatorEntity)

    @Provides
    fun provideDistanceTraveledRepository(distanceTraveledEntity: DistanceTraveledEntity) =
        DistanceTraveledRepositoryImpl(distanceTraveledEntity)

    @Provides
    fun provideDistanceTraveledEntity(metersToKilometersConverterEntity: MetersToKilometersConverterEntity) =
        DistanceTraveledEntity(metersToKilometersConverterEntity)
}