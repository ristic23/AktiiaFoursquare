package com.aktiia.features.details.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel

class DetailsViewModel(

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

    fun fetchPlace(placeId: String) {
        // todo repository fetch
    }
}