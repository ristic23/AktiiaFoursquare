package com.aktiia.core.domain.util

fun String?.getOrNA(): String {
    return if (this.isNullOrBlank())
        "N/A"
    else
        this
}