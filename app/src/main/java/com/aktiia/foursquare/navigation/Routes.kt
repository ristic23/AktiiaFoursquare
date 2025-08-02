package com.aktiia.foursquare.navigation

object Routes {
    const val SEARCH = "search"
    const val PLACE = "place"
    const val FAVORITES = "favorites"
    fun placeWithId(id: String) = "$PLACE/$id"
}