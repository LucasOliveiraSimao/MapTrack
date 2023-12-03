package com.lucassimao.maptrack.domain.di

import com.lucassimao.maptrack.data.SpeedCalculatorRepositoryImpl
import com.lucassimao.maptrack.domain.SpeedAverageUseCase
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
}