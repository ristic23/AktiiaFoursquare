package com.aktiia.features.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aktiia.core.presentation.designsystem.PlaceItem
import com.aktiia.core.presentation.designsystem.WarningScreenState
import com.aktiia.features.search.presentation.SearchAction.OnFavoriteClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClear
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
    ) {
        var searchQuery by rememberSaveable { mutableStateOf("") }
        var active by rememberSaveable { mutableStateOf(false) }
        val toolbarColor = Color(0xFF1976D2)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(toolbarColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val contentColor = Color.White
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
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        color = contentColor
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search icon",
                        tint = contentColor,
                    )
                },
                trailingIcon = {
                    when {
                        state.isLoadingSearch -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = contentColor,
                                strokeWidth = 3.dp,
                            )
                        }
                        searchQuery.isNotBlank() -> {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        searchQuery = ""
                                        onAction(OnSearchClear)
                                    },
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Clear search",
                                tint = contentColor,
                            )
                        }
                    }
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.Transparent,
                    dividerColor = Color.Transparent,
                    inputFieldColors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedTextColor = contentColor,
                        unfocusedTextColor = contentColor,
                        cursorColor = contentColor,
                        focusedPlaceholderColor = contentColor.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = contentColor.copy(alpha = 0.6f),
                        focusedLeadingIconColor = contentColor,
                        unfocusedLeadingIconColor = contentColor,
                        focusedTrailingIconColor = contentColor,
                        unfocusedTrailingIconColor = contentColor,
                    )
                ),
                tonalElevation = 0.dp,
                content = {}
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = { onFavoriteClick() }),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorites",
                tint = contentColor,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Color.DarkGray,
                    strokeWidth = 3.dp,
                )
            }
            state.allIsEmpty -> {
                WarningScreenState(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Filled.Search,
                    title = stringResource(R.string.allIsEmptyTitle),
                    message = stringResource(R.string.allIsEmptyMessage),
                )
            }

            state.isEmptyResult -> {
                WarningScreenState(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Filled.Search,
                    title = stringResource(R.string.emptyResultTitle),
                    message = stringResource(R.string.emptyResultMessage),
                )
            }

            state.isErrorResult -> {
                WarningScreenState(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Filled.Close,
                    title = stringResource(R.string.errorResultTitle),
                    message = stringResource(R.string.errorResultMessage),
                )
            }

            else -> {
                PlacesList(
                    state = state,
                    onAction = onAction,
                    onPlaceClick = onPlaceClick
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.PlacesList(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    onPlaceClick: (String) -> Unit,
) {
    Text(
        text = stringResource(
            id = if (state.showCachedPlaces) {
                R.string.allCachedPlaces
            } else {
                R.string.searchResult
            }
        ),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .align(Alignment.CenterHorizontally),
        style = MaterialTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(
            if (state.showCachedPlaces) {
                state.allCachedPlaces.size
            } else {
                state.searchPlaces.size
            }
        ) { index ->
            val item = if (state.showCachedPlaces) {
                state.allCachedPlaces[index]
            } else {
                state.searchPlaces[index]
            }
            PlaceItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
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