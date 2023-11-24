package com.lucassimao.maptrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucassimao.maptrack.data.RouteRepository
import com.lucassimao.maptrack.data.model.RouteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val repository: RouteRepository
) : ViewModel() {

    fun insertRoute(route: RouteEntity) = viewModelScope.launch {
        repository.insertRoute(route)
    }

    val getAllRoutes = repository.getAllRoutes()

    val getTotalDistance = repository.getTotalDistance()

    val getTotalExecutionTime = repository.getTotalExecutionTime()

}