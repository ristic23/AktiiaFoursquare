package com.aktiia.features.details.presentation

sealed interface DetailsAction {
    data object OnFavoriteClick: DetailsAction
}
