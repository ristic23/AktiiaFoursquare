@file:OptIn(FlowPreview::class)

package com.aktiia.features.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.SearchRepository
import com.aktiia.features.search.presentation.SearchAction.OnFavoriteClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClear
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState(isLoading = true))
        private set

    private var _queryState = MutableStateFlow<String>("")
    var queryState: StateFlow<String> = _queryState

    init {
        searchRepository.getAllCached().onEach { places ->
            state = state.copy(
                allCachedPlaces = places,
                isLoading = false
            )
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            _queryState
                .filter { it.isNotBlank() }
                .debounce(300L)
                .collectLatest { query ->
                    state = state.copy(
                        isLoadingSearch = true,
                        isEmptyResult = false,
                        isErrorResult = false,
                        showCachedPlaces = false,
                    )
                    val result = searchRepository.search(
                        query = query,
//                        ll = "43.3209,21.8958", // Nis
                        ll = "44.787197,20.457273", // Belgrade
                    ).first()
                    state = state.copy(
                        isLoadingSearch = false,
                        isLoading = false,
                    )
                    state = when (result) {
                        is Result.Error -> {
                            state.copy(isErrorResult = true)
                        }

                        is Result.Success -> {
                            state.copy(
                                searchPlaces = result.data,
                                isEmptyResult = result.data.isEmpty()
                            )
                        }
                    }
                }
        }
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
                    _queryState.value = action.query.trim()
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
                state = state.copy(
                    showCachedPlaces = true,
                    isEmptyResult = false,
                    isErrorResult = false,
                )
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _queryState.value = newQuery
    }

}