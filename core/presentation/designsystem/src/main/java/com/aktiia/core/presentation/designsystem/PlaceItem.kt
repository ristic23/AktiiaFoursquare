package com.aktiia.core.presentation.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlaceItem(
    modifier: Modifier = Modifier,
    placeName: String,
    distance: String,
    address: String,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = placeName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .padding(start = 16.dp, end = 40.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                if (distance.isNotBlank()) {
                    Text(
                        text = distance,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 4.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                if (address.isNotBlank()) {
                    Text(
                        text = address,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 4.dp, bottom = 16.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            FavoriteIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp),
                isFavorite = isFavorite,
                onClick = {
                    onFavoriteClick(!isFavorite)
                }
            )
        }
    }
}

@Preview
@Composable
private fun PlaceItemPreview() {
    PlaceItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeName = "Nemir",
        distance = "400m",
        address = "Trg 14 Oktobar, 2/4",
        isFavorite = false,
        onFavoriteClick = {},
        onItemClick = {},
    )
}