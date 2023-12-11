package com.lucassimao.maptrack.domain.di

import com.lucassimao.maptrack.data.repositoryImpl.DistanceTraveledRepositoryImpl
import com.lucassimao.maptrack.data.repositoryImpl.SpeedCalculatorRepositoryImpl
import com.lucassimao.maptrack.domain.usecase.DistanceTraveledUseCase
import com.lucassimao.maptrack.domain.usecase.SpeedAverageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideSpeedAverageUseCase(speedCalculatorRepository: SpeedCalculatorRepositoryImpl) =
        SpeedAverageUseCase(speedCalculatorRepository)

    @Provides
    fun provideDistanceTraveledUseCase(distanceTraveledRepository: DistanceTraveledRepositoryImpl) =
        DistanceTraveledUseCase(distanceTraveledRepository)
}