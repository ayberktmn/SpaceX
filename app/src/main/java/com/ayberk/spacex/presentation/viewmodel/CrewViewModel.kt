package com.ayberk.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.spacex.common.Resource
import com.ayberk.spacex.data.models.crew.CrewFavorite
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import com.ayberk.spacex.data.models.crew.CrewItem
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.usecase.SpaceUseCases
import com.ayberk.spacex.usecase.UpsertCrew
import com.ayberk.spacex.usecase.event.CrewEvent
import com.ayberk.spacex.usecase.event.RocketEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrewViewModel @Inject constructor(private val repository: RetrofitRepository,private val spaceUseCases: SpaceUseCases) : ViewModel() {

    private val _crewState = MutableLiveData<CrewState>()
    val crewState : LiveData<CrewState> get() = _crewState

    fun getCrews() {
        viewModelScope.launch {
            when (val response = repository.getCrew()) {
                is Resource.Success -> {
                    _crewState.value = CrewState(
                        isLoading = false,
                        crewList = response.data
                    )
                    println("CrewViewModel data success")
                }
                is Resource.Fail -> {
                    _crewState.value = CrewState(
                        isLoading = false,
                        errorMessage = response.message,
                    )
                    println("CrewViewModel data Fail")
                }

                is Resource.Error -> {
                    val errorMessage = response.throwable?.message ?: "Unknown error"
                    val detailedError = response.throwable?.localizedMessage ?: "No detailed error message"
                    println("CrewViewModel data error - Error message: $errorMessage, Detailed error: $detailedError")
                    _crewState.value = CrewState(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    private suspend fun upsertCrew(crew: CrewFavorite) {
        spaceUseCases.upsertCrew(crew)
    }

    fun onEvent(event: CrewEvent) {
        when (event) {
            is CrewEvent.UpsertDeleteCrew -> {
                viewModelScope.launch {
                    upsertCrew(event.crew)
                }
            }
        }
    }
}

data class CrewState(
    val isLoading: Boolean = false,
    val crewList: List<com.ayberk.spacex.data.models.crew.CrewItem> = emptyList(),
    val errorMessage: String? = null
)