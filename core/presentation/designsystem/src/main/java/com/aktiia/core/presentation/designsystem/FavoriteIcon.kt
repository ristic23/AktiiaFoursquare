package com.aktiia.core.presentation.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aktiia.core.presentation.designsystem.theme.AktiiaFoursquareTheme

@Composable
fun FavoriteIcon(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    size: Dp = 24.dp,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .size(size)
            .clickable(onClick = onClick),
        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview
@Composable
private fun FavoriteIconPreview() {
    AktiiaFoursquareTheme {
        FavoriteIcon(
            isFavorite = false,
            onClick = {}
        )
    }
}