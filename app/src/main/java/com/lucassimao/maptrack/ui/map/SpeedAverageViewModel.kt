package com.lucassimao.maptrack.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lucassimao.maptrack.domain.SpeedAverageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpeedAverageViewModel @Inject constructor(
    private val useCase: SpeedAverageUseCase
) : ViewModel() {

    fun getSpeedAverage(distanceTraveledInMeters: Float, totalExecutionTime: Long) = liveData {
        val speedAverage = useCase.execute(distanceTraveledInMeters, totalExecutionTime)
        emit(speedAverage)
    }

}