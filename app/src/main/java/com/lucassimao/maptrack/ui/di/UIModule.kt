package com.lucassimao.maptrack.ui.di

import com.lucassimao.maptrack.domain.SpeedAverageUseCase
import com.lucassimao.maptrack.ui.map.SpeedAverageViewModel
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
}