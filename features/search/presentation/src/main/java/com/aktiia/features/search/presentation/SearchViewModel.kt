package com.aktiia.features.search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktiia.features.search.domain.SearchRepository
import com.aktiia.features.search.presentation.SearchAction.OnPlaceClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    init {
        
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is OnPlaceClick -> {}
            is OnSearchClick -> {
                viewModelScope.launch {
                    searchRepository.search(
                        query = action.query,
                        ll = "43.3209,21.8958", // Nis
                    )
                }
            }
        }
    }

}