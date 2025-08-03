package com.aktiia.features.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.details.HoursOpen
import com.aktiia.core.domain.util.getOrNA
import com.aktiia.core.presentation.designsystem.FavoriteIcon
import com.aktiia.core.presentation.designsystem.buildPrefixedText
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreenWrapper(
    viewModel: DetailsViewModel = koinViewModel(),
    onBack: () -> Unit,
    placeId: String,
) {

    LaunchedEffect(key1 = placeId) {
        viewModel.fetchPlace(placeId)
    }

    DetailsScreen(
        state = viewModel.state,
        onBack = onBack,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    state: DetailsState,
    onBack: () -> Unit,
    onAction: (DetailsAction) -> Unit
) {
    val textColor = Color.DarkGray
    val contentColor = Color.DarkGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onBack()
                    },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = contentColor,
            )
            Text(
                text = stringResource(id = R.string.detailsScreenTitle),
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            FavoriteIcon(
                modifier = Modifier
                    .padding(end = 8.dp),
                size = 32.dp,
                isFavorite = state.item?.isFavorite == true,
                onClick = {
                    onAction(DetailsAction.OnFavoriteClick)
                }
            )
        }
        Text(
            text = state.item?.name ?: "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 16.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = state.item?.description ?: stringResource(R.string.descEmpty),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.telPrefix),
                value = state.item?.tel.getOrNA(),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.websitePrefix),
                value = state.item?.website.getOrNA(),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.hoursPrefix),
                value = state.item?.hours?.display.getOrNA(),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.isOpenPrefix),
                value = stringResource(
                    if (state.item?.hours?.isOpen == true) {
                        R.string.open
                    } else {
                        R.string.closed
                    }
                ),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.ratingPrefix),
                value = (state.item?.rating ?: 0.0).toString(),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "photos needed",
            modifier = Modifier
                .padding(bottom = 4.dp),
            color = Color.Red,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.distancePrefix),
                value = state.item?.distance.getOrNA(),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = buildPrefixedText(
                prefix = stringResource(R.string.addressPrefix),
                value = state.item?.address.getOrNA(),
                textColor = textColor
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
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
                hours = HoursOpen(
                    display = "Mon-Sat 9:00-23:00; Sun 12:00-21:00",
                    isOpen = true
                ),
                rating = 6.9,
                photos = listOf(),
            )
        ),
        onAction = {},
        onBack = {},
    )
}