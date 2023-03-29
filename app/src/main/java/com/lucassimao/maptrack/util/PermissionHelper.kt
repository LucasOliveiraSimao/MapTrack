package com.lucassimao.maptrack.util

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object PermissionHelper {
    fun hasLocationPermissions(context: Context) = EasyPermissions.hasPermissions(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}