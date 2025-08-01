package com.aktiia.features.search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aktiia.features.search.presentation.SearchAction.OnPlaceClick
import com.aktiia.features.search.presentation.SearchAction.OnSearchClick

class SearchViewModel : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    fun onAction(action: SearchAction) {
//        when (action) {
//            is OnPlaceClick -> onPlaceClick
//            is OnSearchClick -> onSearchClick
//        }
    }

}