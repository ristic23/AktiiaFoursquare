package com.aktiia.foursquare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aktiia.features.details.presentation.DetailsScreenWrapper
import com.aktiia.features.favorites.presentation.FavoritesScreenWrapper
import com.aktiia.features.search.presentation.SearchScreenWrapper


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(route = Routes.SEARCH) {
            SearchScreenWrapper(
                onPlaceClick = {
                    navController.navigate(Routes.placeWithId(it))
                },
                onFavoriteClick = {
                    navController.navigate(Routes.FAVORITES)
                },
            )
        }
        composable(
            route = "place/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            DetailsScreenWrapper(
                placeId = placeId
            )
        }
        composable(route = Routes.FAVORITES) {
            FavoritesScreenWrapper(
                onPlaceClick = {
                    navController.navigate(Routes.placeWithId(it))
                }
            )
        }
    }
}
