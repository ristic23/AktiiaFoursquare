package com.aktiia.features.favorites.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.aktiia.features.favorites.domain.FavoritesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    var state by mutableStateOf(FavoritesState(
        isLoading = true
    ))
        private set

    init {

        favoritesRepository.getFavoritePlaces().onEach {
            state = state.copy(
                isLoading = false,
                favorites = it,
                isEmptyResult = it.isEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: FavoritesAction) {
        when (action) {
            is FavoritesAction.OnFavoriteOffClicked -> {
                viewModelScope.launch {
                    favoritesRepository.updateFavoriteStatus(action.id)
                }
            }
        }
    }
}