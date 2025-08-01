package com.aktiia.core.presentation.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlaceItem(
    modifier: Modifier = Modifier,
    placeName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = placeName,
            modifier = Modifier
                .padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
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
        onClick = {}
    )
}