package com.ayberk.spacex.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.usecase.SpaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class viewmodelfav @Inject constructor(
    private val spaceRoomDAO: SpaceRoomDAO,
    private val spaceUseCases: SpaceUseCases
) : ViewModel() {

    private val _favoriteRocketsLiveData = MutableLiveData<List<FavoriteRockets>>()
    val favoriteRocketsLiveData: LiveData<List<FavoriteRockets>> = _favoriteRocketsLiveData
    var onRocketListEmpty: (() -> Unit)? = null

    init {
        getAllFavoriteRockets()
    }

    fun getAllFavoriteRockets() {
        viewModelScope.launch {
            val rockets = withContext(Dispatchers.IO) {
                spaceRoomDAO.getAllRockets()
            }
            _favoriteRocketsLiveData.postValue(rockets)
        }
    }

    fun clearRoomIfNotEmpty() {
        if (favoriteRocketsLiveData.value?.isNotEmpty() == true) {
            viewModelScope.launch {
                spaceUseCases.clearRoom()
            }
        } else {
            onRocketListEmpty?.invoke() // Liste boşsa callback'i çağır
        }
    }

    fun deleteRockets(rockets: FavoriteRockets) {
        viewModelScope.launch {
            spaceUseCases.deleteRockets(rockets)
        }
    }
}