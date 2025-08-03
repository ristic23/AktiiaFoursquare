package com.aktiia.features.details.presentation

import com.aktiia.core.domain.PlaceDetailsData

data class DetailsState (
    val item: PlaceDetailsData? = null,
    val isLoading: Boolean = true,
    val isErrorResult: Boolean = false,
)