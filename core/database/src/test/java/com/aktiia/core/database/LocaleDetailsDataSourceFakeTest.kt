package com.aktiia.core.database

import com.aktiia.core.database.common.place
import com.aktiia.core.database.details.LocaleDetailsDataSourceFake
import com.aktiia.core.domain.util.DataError
import com.aktiia.core.domain.util.Result
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocaleDetailsDataSourceFakeTest {

    private lateinit var detailsFake: LocaleDetailsDataSourceFake

    @Before
    fun setUp() {
        detailsFake = LocaleDetailsDataSourceFake()
    }

    @Test
    fun `add multiple places and getById success`() = runTest {
        detailsFake.upsert(place("1", "Coffee", true))
        detailsFake.upsert(place("2", "Coffee #2"))
        detailsFake.upsert(place("3", "Bar"))

        val result = detailsFake.getById("1")
        assertTrue(result is Result.Success && result.data.id == "1")
    }

    @Test
    fun `add multiple places and getById error`() = runTest {
        detailsFake.upsert(place("1", "Coffee", true))
        detailsFake.upsert(place("2", "Coffee #2"))
        detailsFake.upsert(place("3", "Bar"))

        val result = detailsFake.getById("4")
        assertTrue(result is Result.Error && result.error == DataError.Local.ERROR_FETCHING)
    }

}