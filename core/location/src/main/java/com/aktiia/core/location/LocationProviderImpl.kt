@file:OptIn(ExperimentalCoroutinesApi::class)

package com.aktiia.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.aktiia.core.domain.location.LocationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationProviderImpl(
    private val context: Context,
): LocationProvider {

    private val fusedClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getLocation(): String {
        val fallBackLocation = "44.787197,20.457273" //"44.787197,20.457273" BG, 43.3209,21.8958" NIs
        if (!checkPermission() || !hasLocationEnabled()) return fallBackLocation

        return suspendCancellableCoroutine { cont ->

            fusedClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        result?.let {
                            cont.resume("${it.latitude},${it.longitude}")
                        } ?: cont.resume(fallBackLocation)
                    } else {
                        cont.resume(fallBackLocation)
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    result?.let {
                        cont.resume("${it.latitude},${it.longitude}")
                    } ?: cont.resume(fallBackLocation)
                }

                addOnFailureListener {
                    cont.resume(fallBackLocation)
                }

                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

    override fun checkPermission(): Boolean =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    override fun hasLocationEnabled(): Boolean {
        val locationManager = context.getSystemService<LocationManager>()!!
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}

