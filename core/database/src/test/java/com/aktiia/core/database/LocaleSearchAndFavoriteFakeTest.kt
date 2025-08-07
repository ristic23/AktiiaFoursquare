package com.aktiia.core.database

import com.aktiia.core.database.locale.LocaleFavoritesDataSourceFake
import com.aktiia.core.database.locale.LocaleSearchDataSourceFake
import com.aktiia.core.domain.PlaceData
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.flow.first

class LocaleSearchAndFavoriteFakeTest {

    private lateinit var favoritesFake: LocaleFavoritesDataSourceFake
    private lateinit var placesFake: LocaleSearchDataSourceFake

    private val place1 = PlaceData("1", "Place One", "100", "Address 1", false)
    private val place2 = PlaceData("2", "Place Two", "200", "Address 2", false)
    private val place3 = PlaceData("3", "Place Three", "300", "Address 3", false)

    @BeforeEach
    fun setUp() {
        placesFake = LocaleSearchDataSourceFake()
        favoritesFake = LocaleFavoritesDataSourceFake(placesFake)

        runBlocking {
            placesFake.upsertPlaces(listOf(place1, place2, place3))
        }
    }

    @Test
    fun `addFavoriteStatus should add item to favorites`() = runBlocking {
        favoritesFake.addFavoriteStatus("1")
        val result = favoritesFake.isFavorite("1")
        assertTrue(result is Result.Success && result.data)
    }

    @Test
    fun `removeFavoriteStatus should remove item from favorites`() = runBlocking {
        favoritesFake.addFavoriteStatus("2")
        favoritesFake.removeFavoriteStatus("2")
        val result = favoritesFake.isFavorite("2")
        assertTrue(result is Result.Success && !result.data)
    }

    @Test
    fun `isFavorite returns false for unknown id`() = runBlocking {
        val result = favoritesFake.isFavorite("999")
        assertTrue(result is Result.Success && !result.data)
    }

    @Test
    fun `getFavoritePlaces returns only favorite places`() = runBlocking {
        favoritesFake.addFavoriteStatus("1")
        favoritesFake.addFavoriteStatus("3")

        val result = favoritesFake.getFavoritePlaces().first()
        assertEquals(2, result.size)
        assertTrue(result.any { it.id == "1" })
        assertTrue(result.any { it.id == "3" })
    }

    @Test
    fun `getFavoritePlaces returns empty list when no favorites`() = runBlocking {
        val result = favoritesFake.getFavoritePlaces().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `add and remove multiple favorites`() = runBlocking {
        favoritesFake.addFavoriteStatus("1")
        favoritesFake.addFavoriteStatus("2")
        favoritesFake.removeFavoriteStatus("1")

        val result = favoritesFake.getFavoritePlaces().first()
        assertEquals(1, result.size)
        assertEquals("2", result.first().id)
    }

    @Test
    fun `check if upsert added correct item on Before`() = runBlocking {
        val result = placesFake.getPlaces().first()
        assertEquals(3, result.size)
        assertTrue(result.any { it.id == "1" })
        assertTrue(result.any { it.id == "2" })
        assertTrue(result.any { it.id == "3" })
    }
}