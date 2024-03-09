package com.ayberk.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.spacex.data.models.crew.CrewFavorite
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.data.room.crewRoom.CrewRoomDAO
import com.ayberk.spacex.usecase.SpaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CrewFavoriteViewModel @Inject constructor(
    private val roomDAO: CrewRoomDAO,
    private val spaceUseCases: SpaceUseCases
) : ViewModel() {

    private val _favoriteCrewLiveData = MutableLiveData<List<CrewFavorite>>()
    val favoriteCrewLiveData: LiveData<List<CrewFavorite>> = _favoriteCrewLiveData
    var onCrewListEmpty: (() -> Unit)? = null

    init {
        getAllFavoriteCrew()
    }

    fun getAllFavoriteCrew() {
        viewModelScope.launch {
            val rockets = withContext(Dispatchers.IO) {
                roomDAO.getAllCrew()
            }
            _favoriteCrewLiveData.postValue(rockets)
        }
    }

    fun clearRoomIfNotEmpty() {
        if (favoriteCrewLiveData.value?.isNotEmpty() == true) {
            viewModelScope.launch {
                spaceUseCases.clearRoomCrew()
            }
        } else {
            onCrewListEmpty?.invoke() // Liste boşsa callback'i çağır
        }
    }

    fun deleteCrew(crews: CrewFavorite) {
        viewModelScope.launch {
            spaceUseCases.deleteCrew(crews)
        }
    }
}