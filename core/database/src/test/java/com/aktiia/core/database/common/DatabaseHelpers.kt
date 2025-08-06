package com.aktiia.core.database.common

import com.aktiia.core.domain.PlaceDetailsData
import com.aktiia.core.domain.details.HoursOpen
import kotlin.String

fun <T> MutableList<T>.upsert(
    newItems: List<T>,
    areItemsTheSame: (T, T) -> Boolean
) {
    newItems.forEach { newItem ->
        val index = indexOfFirst { existingItem -> areItemsTheSame(existingItem, newItem) }
        if (index != -1) {
            this[index] = newItem
        } else {
            this.add(newItem)
        }
    }
}

fun <T> MutableList<T>.upsert(
    newItem: T,
    areItemsTheSame: (T, T) -> Boolean
) {
    val index = this.indexOfFirst { existingItem ->
        areItemsTheSame(existingItem, newItem)
    }
    if (index != -1) {
        this[index] = newItem
    } else {
        this.add(newItem)
    }
}

fun place(
    id: String,
    name: String,
    isFavorite: Boolean = false,
) = PlaceDetailsData(
    id = id,
    name = name,
    description = "desc",
    tel = "0011223344",
    website = "web",
    rating = 9.9,
    distance = "99",
    address = "Bulevar bb",
    isFavorite = isFavorite,
    hours = HoursOpen("", true),
    photos = emptyList(),
)