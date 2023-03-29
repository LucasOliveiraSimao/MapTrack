package com.lucassimao.maptrack.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.databinding.FragmentMapsBinding
import com.lucassimao.maptrack.util.Constants.GOOGLE_MAPS_CAMERA_ZOOM_VALUE
import com.lucassimao.maptrack.util.Constants.PERMISSION_REQUEST_CODE
import com.lucassimao.maptrack.util.PermissionHelper.hasLocationPermissions
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: FragmentMapsBinding

    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        requestPermissions()
        binding.mapView.onCreate(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentLocation()

    }

    private fun requestPermissions() {
        if (hasLocationPermissions(requireContext())) {
            return
        }
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.you_need_to_accept_location_permissions_to_use_this_app),
            PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (hasLocationPermissions(requireContext())) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->

                val cityLatLng = LatLng(location.latitude, location.longitude)

                binding.mapView.getMapAsync { map ->
                    with(map) {
                        addMarker(MarkerOptions().position(cityLatLng))
                        mapType = GoogleMap.MAP_TYPE_SATELLITE
                        moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                cityLatLng,
                                GOOGLE_MAPS_CAMERA_ZOOM_VALUE
                            )
                        )

                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

}