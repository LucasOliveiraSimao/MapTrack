package com.lucassimao.maptrack.data.di

import com.lucassimao.maptrack.data.SpeedCalculatorRepositoryImpl
import com.lucassimao.maptrack.data.entity.SpeedCalculatorEntity
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
}