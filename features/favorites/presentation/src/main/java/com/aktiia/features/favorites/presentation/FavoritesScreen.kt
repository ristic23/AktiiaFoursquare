package com.aktiia.features.favorites.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aktiia.core.presentation.designsystem.PlaceItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun FavoritesScreenWrapper(
    onPlaceClick: (String) -> Unit,
    viewModel: FavoritesViewModel = koinViewModel(),
) {

    FavoritesScreen(
        state = viewModel.state,
        onPlaceClick = onPlaceClick,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreen(
    state: FavoritesState,
    onPlaceClick: (String) -> Unit,
    onAction: (FavoritesAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.favorites.size) { index ->
                val item = state.favorites[index]
                PlaceItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    placeName = item.name,
                    distance = item.distance,
                    address = item.address,
                    isFavorite = item.isFavorite,
                    onFavoriteClick = {
                        onAction(FavoritesAction.OnFavoriteOffClicked(item.id))
                    },
                    onItemClick = { onPlaceClick(item.id) },
                )
            }
        }

    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    FavoritesScreen(
        state = FavoritesState(),
        onAction = {},
        onPlaceClick = {},
    )
}