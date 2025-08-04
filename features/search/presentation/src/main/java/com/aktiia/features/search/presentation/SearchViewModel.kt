@file:OptIn(FlowPreview::class)

package com.aktiia.features.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.location.LocationProvider
import com.aktiia.core.domain.util.Result
import com.aktiia.features.search.domain.SearchRepository
import com.aktiia.features.search.presentation.SearchAction.DismissRationaleDialog
import com.aktiia.features.search.presentation.SearchAction.OnFavoriteClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClear
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick
import com.aktiia.features.search.presentation.SearchAction.SubmitLocationPermissionInfo
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
    private val searchRepository: SearchRepository,
    private val locationProvider: LocationProvider,
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
                        ll = locationProvider.getLocation()
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
        checkWarningBanner()
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
                    val isFavorite = when (val result = searchRepository.updateFavoriteStatus(
                        id = action.id,
                        isFavorite = action.isFavorite
                    )) {
                        is Result.Error -> false
                        is Result.Success -> result.data
                    }
                    if (!state.showCachedPlaces) {
                        state = state.copy(
                            searchPlaces = updateFavoriteStatus(
                                state.searchPlaces,
                                action.id,
                                isFavorite
                            )
                        )
                    }
                }
            }

            is OnSearchClear -> {
                state = state.copy(
                    showCachedPlaces = true,
                    isEmptyResult = false,
                    isErrorResult = false,
                )
            }

            is DismissRationaleDialog -> {
               state = state.copy(
                    showLocationRationale = false
                )
            }
            is SubmitLocationPermissionInfo -> {
                state = state.copy(
                    showLocationRationale = action.showLocationRationale
                )
            }
        }
    }

    private fun updateFavoriteStatus(
        list: List<PlaceData>,
        id: String,
        newStatus: Boolean
    ): List<PlaceData> {
        return list.map { place ->
            if (place.id == id) {
                place.copy(isFavorite = newStatus)
            } else {
                place
            }
        }
    }

    private fun checkWarningBanner() {
        val isLocationPermissionGranted = locationProvider.checkPermission()
        val isLocationEnabled = locationProvider.hasLocationEnabled()
        state = state.copy(
            isLocationEnabled = isLocationEnabled,
            isLocationPermissionGranted = isLocationPermissionGranted
        )
    }

    fun onQueryChange(newQuery: String) {
        _queryState.value = newQuery
    }

}