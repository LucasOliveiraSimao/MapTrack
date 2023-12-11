package com.lucassimao.maptrack.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lucassimao.maptrack.data.core.ListOfLocations
import com.lucassimao.maptrack.domain.usecase.DistanceTraveledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DistanceTraveledViewModel @Inject constructor(
    private val useCase: DistanceTraveledUseCase
) : ViewModel() {

    fun getDistanceTraveled(routePolylines: List<ListOfLocations>) = liveData {
        val distanceTraveled = useCase.execute(routePolylines)
        emit(distanceTraveled)
    }

}