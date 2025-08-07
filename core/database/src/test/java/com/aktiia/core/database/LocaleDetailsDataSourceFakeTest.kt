package com.aktiia.core.database

import com.aktiia.core.database.common.place
import com.aktiia.core.database.details.LocaleDetailsDataSourceFake
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocaleDetailsDataSourceFakeTest {

    private lateinit var detailsFake: LocaleDetailsDataSourceFake

    @BeforeEach
    fun setUp() {
        detailsFake = LocaleDetailsDataSourceFake()
    }

    @Test
    fun `add multiple places and getById success`() = runBlocking {
        detailsFake.upsert(place("1", "Coffee", true))
        detailsFake.upsert(place("2", "Coffee #2"))
        detailsFake.upsert(place("3", "Bar"))

        val result = detailsFake.getById("1")
        assertTrue(result is Result.Success && result.data.id == "1")
    }

    @Test
    fun `add multiple places and getById error`() = runBlocking {
        detailsFake.upsert(place("1", "Coffee", true))
        detailsFake.upsert(place("2", "Coffee #2"))
        detailsFake.upsert(place("3", "Bar"))

        val result = detailsFake.getById("4")
        assertTrue(result is Result.Error && result.error == DataError.Local.ERROR_FETCHING)
    }

}