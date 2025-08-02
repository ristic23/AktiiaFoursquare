package com.aktiia.features.details.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.domain.DetailsRepository

class DetailsViewModel(
    private val detailsRepository: DetailsRepository
): ViewModel() {

    var state by mutableStateOf(DetailsState())
        private set

    fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.OnFavoriteClick -> {
                // todo switch favorite state
//                state.item?.id
//                state.item?.isFavorite
            }
        }
    }

    suspend fun fetchPlace(placeId: String) {
        when (val result = detailsRepository.fetchDetails(placeId)) {
            is Result.Error -> {
                // todo
            }
            is Result.Success -> {
                state = state.copy(item = result.data)
            }
        }
    }
}