package com.lucassimao.maptrack.ui.di

import com.lucassimao.maptrack.domain.usecase.DistanceTraveledUseCase
import com.lucassimao.maptrack.domain.usecase.SpeedAverageUseCase
import com.lucassimao.maptrack.ui.map.viewmodel.DistanceTraveledViewModel
import com.lucassimao.maptrack.ui.map.viewmodel.SpeedAverageViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UIModule {
    @Provides
    fun provideSpeedAverageViewModel(useCase: SpeedAverageUseCase) =
        SpeedAverageViewModel(useCase)

    @Provides
    fun provideDistanceTraveledViewModel(useCase: DistanceTraveledUseCase) =
        DistanceTraveledViewModel(useCase)
}