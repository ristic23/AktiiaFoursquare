package com.aktiia.features.search.presentation

import com.aktiia.core.domain.PlaceData

data class SearchState(
    val searchQuery: String = "",
    val searchPlaces: List<PlaceData> = emptyList<PlaceData>(),
    val allCachedPlaces: List<PlaceData> = emptyList<PlaceData>(),
    val isLoadingSearch: Boolean = false,
    val isLoading: Boolean = false,
    val isEmptyResult: Boolean = false,
    val isErrorResult: Boolean = false,
    val showCachedPlaces: Boolean = true,
    val showLocationRationale: Boolean = false,
) {
    val allIsEmpty: Boolean = searchPlaces.isEmpty() && allCachedPlaces.isEmpty()
}
