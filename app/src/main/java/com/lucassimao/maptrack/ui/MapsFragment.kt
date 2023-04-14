package com.lucassimao.maptrack.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.databinding.FragmentMapsBinding
import com.lucassimao.maptrack.service.MapTrackService
import com.lucassimao.maptrack.util.Constants.GOOGLE_MAPS_CAMERA_ZOOM_VALUE
import com.lucassimao.maptrack.util.Constants.PERMISSION_REQUEST_CODE
import com.lucassimao.maptrack.util.Constants.START_OR_RESUME_SERVICE_ACTION
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

        binding.mapsBtnStart.setOnClickListener {
            sendCommandToService(START_OR_RESUME_SERVICE_ACTION)
        }

        binding.mapView.getMapAsync { map ->
            with(map) {
                val position = LatLng(-3.140788, -58.452946)
                addMarker(MarkerOptions().position(position))
                mapType = GoogleMap.MAP_TYPE_SATELLITE
                moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        position,
                        GOOGLE_MAPS_CAMERA_ZOOM_VALUE
                    )
                )

            }

        }

    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), MapTrackService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
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

}