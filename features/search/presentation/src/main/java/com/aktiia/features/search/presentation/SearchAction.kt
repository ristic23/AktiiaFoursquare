package com.aktiia.features.search.presentation

sealed interface SearchAction {
    data class OnSearchClick(val query: String) : SearchAction
    data object OnSearchClear: SearchAction
    data class OnFavoriteClick(val id: String, val isFavorite: Boolean): SearchAction
    data object DismissRationaleDialog: SearchAction
    data class SubmitLocationPermissionInfo(val showLocationRationale: Boolean): SearchAction
}