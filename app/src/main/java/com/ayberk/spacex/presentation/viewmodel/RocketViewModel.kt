package com.ayberk.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.spacex.common.Resource
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import com.ayberk.spacex.data.models.rockets.Rockets
import com.ayberk.spacex.data.models.rockets.RocketsItem
import com.ayberk.spacex.presentation.ui.RocketEvent
import com.ayberk.spacex.usecase.SpaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketViewModel @Inject constructor(private val repository: RetrofitRepository,private val spaceUseCases: SpaceUseCases) : ViewModel(){

    private val _rocketState = MutableLiveData<RocketState>()
    val rocketState : LiveData<RocketState> get() = _rocketState

    fun getRockets() {
        _rocketState.value = RocketState(isLoading = true)
        viewModelScope.launch {
            when (val response = repository.getRockets()) {
                is Resource.Success -> {
                    _rocketState.value = RocketState(
                        isLoading = false,
                        rocketsList = response.data
                    )
                    println("SpellsViewModel data success")
                }
                is Resource.Fail -> {
                    _rocketState.value = RocketState(
                        isLoading = false,
                        errorMessage = response.message,
                    )
                    println("SpellsViewModel data Fail")
                }

                is Resource.Error -> {
                    val errorMessage = response.throwable?.message ?: "Unknown error"
                    val detailedError = response.throwable?.localizedMessage ?: "No detailed error message"
                    println("SpellsViewModel data error - Error message: $errorMessage, Detailed error: $detailedError")
                    _rocketState.value = RocketState(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }
    private suspend fun upsertRockets(rocket: FavoriteRockets) {
        spaceUseCases.upsertRocket(rocket = rocket)
    }

    fun onEvent(event: RocketEvent) {
        when (event) {
            is RocketEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    upsertRockets(event.rocket)
                }
            }
        }
    }
}

data class RocketState(
    val isLoading: Boolean = false,
    val rocketsList: List<com.ayberk.spacex.data.models.rockets.RocketsItem> = emptyList(),
    val errorMessage: String? = null
)