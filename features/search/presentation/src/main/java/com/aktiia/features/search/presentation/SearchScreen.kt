package com.aktiia.features.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aktiia.core.presentation.designsystem.PlaceItem
import com.aktiia.features.search.presentation.SearchAction.OnPlaceClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreenWrapper(
//    onSearchClick: (String) -> Unit,
    onPlaceClick: (String) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    SearchScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        var searchQuery by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth(),
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
                    onClick = { onAction(OnPlaceClick) }
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
        onAction = {}
    )
}