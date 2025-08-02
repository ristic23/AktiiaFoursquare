package com.aktiia.core.data.network

import com.aktiia.core.domain.util.DataError
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

fun mapThrowableToNetworkError(e: Throwable): DataError.Network {
    return when (e) {
        is retrofit2.HttpException -> mapHttpCodeToNetworkError(e.code())
        is UnresolvedAddressException,
        is UnknownHostException,
        is SocketTimeoutException,
        is ConnectException -> DataError.Network.NO_INTERNET
        is SerializationException -> DataError.Network.SERIALIZATION
        else -> DataError.Network.UNKNOWN
    }
}

fun mapHttpCodeToNetworkError(code: Int): DataError.Network = when (code) {
    401 -> DataError.Network.UNAUTHORIZED
    408 -> DataError.Network.REQUEST_TIMEOUT
    409 -> DataError.Network.CONFLICT
    413 -> DataError.Network.PAYLOAD_TOO_LARGE
    429 -> DataError.Network.TOO_MANY_REQUESTS
    in 500..599 -> DataError.Network.SERVER_ERROR
    else -> DataError.Network.UNKNOWN
}