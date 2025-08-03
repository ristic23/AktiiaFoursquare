package com.aktiia.features.search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.SearchRepository
import com.aktiia.features.search.presentation.SearchAction.OnFavoriteClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClear
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState(
        isLoading = true
    ))
        private set

    init {
        searchRepository.getPlaces().onEach { places ->
            state = state.copy(
                allCachedPlaces = places,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is OnSearchClick -> {
                viewModelScope.launch {
                    state = state.copy(
                        isLoadingSearch = true,
                        isEmptyResult = false,
                        isErrorResult = false,
                        showCachedPlaces = false,
                        )
                    when (val result =searchRepository.search(
                        query = action.query,
                        ll = "43.3209,21.8958", // Nis
                    )) {
                        is Result.Error -> {
                            state = state.copy(isErrorResult = true)
                        }
                        is Result.Success -> {
                            state = state.copy(
                                searchPlaces = result.data,
                                isEmptyResult = result.data.isEmpty()
                            )
                        }
                    }
                    state = state.copy(
                        isLoadingSearch = false,
                        isLoading = false,
                    )
                }
            }
            is OnFavoriteClick -> {
                viewModelScope.launch {
                    searchRepository.updateFavoriteStatus(
                        id = action.id,
                        isFavorite = action.isFavorite
                    )
                }
            }

            OnSearchClear -> {
                state = state.copy(showCachedPlaces = true)
            }
        }
    }

}