package com.aktiia.features.details.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aktiia.core.domain.util.Result
import com.aktiia.features.details.domain.DetailsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

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
        viewModelScope.launch {
            detailsRepository.fetchDetails(placeId)
                .collectLatest { result ->
                    state = when (result) {
                        is Result.Error -> {
                            state.copy(
                                isErrorResult = true,
                                isLoading = false
                            )
                        }

                        is Result.Success -> {
                            state.copy(
                                item = result.data,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}