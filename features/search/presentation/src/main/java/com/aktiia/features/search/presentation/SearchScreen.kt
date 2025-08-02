package com.aktiia.features.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aktiia.core.presentation.designsystem.PlaceItem
import com.aktiia.features.search.presentation.SearchAction.OnFavoriteClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreenWrapper(
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    SearchScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onPlaceClick = onPlaceClick,
        onFavoriteClick = onFavoriteClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    state: SearchState,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    onAction: (SearchAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        var searchQuery by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                modifier = Modifier
                    .weight(1f),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {
                    active = false
                    onAction(OnSearchClick(searchQuery))
                },
                active = active,
                onActiveChange = { /*active = it*/ },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                searchQuery = ""
                            },
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                ),
                tonalElevation = 0.dp,
                content = {}
            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Icon(
//                modifier = Modifier
//                    .size(32.dp)
//                    .clickable(onClick = {}),
//                imageVector = Icons.Filled.Settings,
//                contentDescription = "Database",
//            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = { onFavoriteClick() }),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Database",
                tint = Color.Red,
            )

        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.searchResult.size) { index ->
                val item = state.searchResult[index]
                PlaceItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    placeName = item.name,
                    distance = item.distance,
                    address = item.address,
                    isFavorite = item.isFavorite,
                    onFavoriteClick = {
                        onAction(OnFavoriteClick(item.id, it))
                    },
                    onItemClick = { onPlaceClick(item.id) },
                )
            }
        }

    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = SearchState(),
        onAction = {},
        onPlaceClick = {},
        onFavoriteClick = {},
    )
}