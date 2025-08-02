package com.aktiia.features.search.presentation

import com.aktiia.features.search.domain.PlaceData

data class SearchState(
    val searchQuery: String = "",
    val searchResult: List<PlaceData> = emptyList<PlaceData>(),
    val isLoading: Boolean = false,
    val isEmptyResult: Boolean = false,
    val isErrorResult: Boolean = false,
)
