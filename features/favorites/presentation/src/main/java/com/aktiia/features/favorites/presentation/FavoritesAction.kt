package com.aktiia.features.favorites.presentation

sealed interface FavoritesAction {
    data class OnFavoriteOffClicked(val id: String, val isFavorite: Boolean): FavoritesAction
}