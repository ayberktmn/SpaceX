package com.ayberk.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.spacex.common.Resource
import com.ayberk.spacex.di.retrofit.RetrofitRepository
import com.ayberk.spacex.presentation.models.crew.CrewItem
import com.ayberk.spacex.presentation.models.dragons.DragonsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonViewModel @Inject  constructor(private val repository: RetrofitRepository) : ViewModel(){

    private val _dragonState = MutableLiveData<DragonState>()
    val dragonState : LiveData<DragonState> get() = _dragonState

    fun getDragons() {
        viewModelScope.launch {
            when (val response = repository.getDragons()) {
                is Resource.Success -> {
                    _dragonState.value = DragonState(
                        isLoading = false,
                        dragonList = response.data
                    )
                    println("CrewViewModel data success")
                }
                is Resource.Fail -> {
                    _dragonState.value = DragonState(
                        isLoading = false,
                        errorMessage = response.message,
                    )
                    println("CrewViewModel data Fail")
                }

                is Resource.Error -> {
                    val errorMessage = response.throwable?.message ?: "Unknown error"
                    val detailedError = response.throwable?.localizedMessage ?: "No detailed error message"
                    println("CrewViewModel data error - Error message: $errorMessage, Detailed error: $detailedError")
                    _dragonState.value = DragonState(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }
}


data class DragonState(
    val isLoading: Boolean = false,
    val dragonList: List<DragonsItem> = emptyList(),
    val errorMessage: String? = null
)