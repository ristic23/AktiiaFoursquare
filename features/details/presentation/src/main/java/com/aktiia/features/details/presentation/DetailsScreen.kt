package com.aktiia.features.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.presentation.designsystem.FavoriteIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreenWrapper(
    viewModel: DetailsViewModel = koinViewModel(),
    placeId: String,
) {

    LaunchedEffect(key1 = placeId) {
        viewModel.fetchPlace(placeId)
    }

    DetailsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    state: DetailsState,
    onAction: (DetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = state.item?.name ?: "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = state.item?.description ?: "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = state.item?.tel ?: "",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = state.item?.website ?: "",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = state.item?.hours ?: "",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = (state.item?.rating ?: 0.0).toString(),
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "photos needed",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = state.item?.distance ?: "",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = state.item?.address ?: "",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium
        )
        FavoriteIcon(
            isFavorite = state.item?.isFavorite ?: false,
            onClick = {
                onAction(DetailsAction.OnFavoriteClick)
            }
        )
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DetailsScreen(
        state = DetailsState(
            item = PlaceDetailsData(
                id = "1",
                name = "Nemir",
                distance = "43m",
                address = "Vozdova, 33",
                isFavorite = false,
                description = "Opis lokala",
                tel = "+3816677116677",
                website = "www.food.com",
                hours = "Mon-Sat 9:00-23:00; Sun 12:00-21:00",
                rating = 6.9,
                photos = "",
            )
        ),
        onAction = {},
    )
}