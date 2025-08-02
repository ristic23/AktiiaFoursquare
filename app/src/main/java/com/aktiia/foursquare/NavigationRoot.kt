package com.aktiia.foursquare

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aktiia.features.search.presentation.SearchScreenWrapper


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        composable(route = "search") {
            SearchScreenWrapper(
                onPlaceClick = {
//                    navController.navigate("place")
                },
                onFavoriteClick = {
//                    navController.navigate("favorites")
                },
            )
        }
        composable(route = "place") {
            // Todo screen
        }
        composable(route = "favorites") {
            // Todo screen and go to places
        }
    }
}
