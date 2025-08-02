package com.aktiia.features.favorites.presentation

import com.aktiia.core.domain.PlaceData

data class FavoritesState(
    val favorites: List<PlaceData> = emptyList<PlaceData>(),
    val isLoading: Boolean = false,
    val isEmptyResult: Boolean = false,
    val isErrorResult: Boolean = false,
)
