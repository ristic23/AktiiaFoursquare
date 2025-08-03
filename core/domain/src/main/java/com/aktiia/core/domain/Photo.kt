package com.aktiia.core.domain

data class Photo(
    val id: String,
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int,
) {
    val url = "${prefix}500x500$suffix"
}
