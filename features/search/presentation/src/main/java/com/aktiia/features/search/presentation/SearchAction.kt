package com.aktiia.features.search.presentation

sealed interface SearchAction {
    data object OnSearchClick : SearchAction
    data object OnPlaceClick: SearchAction
}