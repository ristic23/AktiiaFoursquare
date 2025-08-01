package com.aktiia.features.search.presentation

data class SearchState(
    val searchQuery: String = "",
    val searchResult: List<String> = emptyList<String>(),
    val isLoading: Boolean = false,
    val isEmptyResult: Boolean = false,
    val isErrorResult: Boolean = false,
)
