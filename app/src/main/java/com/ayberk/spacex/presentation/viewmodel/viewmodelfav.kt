package com.ayberk.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import com.ayberk.spacex.data.room.SpaceRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class viewmodelfav @Inject constructor(
    private val spaceRoomDAO: SpaceRoomDAO // Room DAO
) : ViewModel() {

    private val _favoriteRocketsLiveData = MutableLiveData<List<FavoriteRockets>>()
    val favoriteRocketsLiveData: LiveData<List<FavoriteRockets>> = _favoriteRocketsLiveData

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
}