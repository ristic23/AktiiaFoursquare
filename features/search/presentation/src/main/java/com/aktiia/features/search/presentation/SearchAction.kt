package com.aktiia.features.search.presentation

sealed interface SearchAction {
    data class OnSearchClick(val query: String) : SearchAction
    data object OnPlaceClick: SearchAction
}