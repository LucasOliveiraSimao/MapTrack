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
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.databinding.FragmentMapsBinding
import com.lucassimao.maptrack.service.NavigationMapTrackService
import com.lucassimao.maptrack.util.Constants.GOOGLE_MAPS_CAMERA_ZOOM_VALUE
import com.lucassimao.maptrack.util.Constants.PERMISSION_REQUEST_CODE
import com.lucassimao.maptrack.util.Constants.START_OR_RESUME_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.STOP_SERVICE_ACTION
import com.lucassimao.maptrack.util.ListOfLocations
import com.lucassimao.maptrack.util.PermissionUtil.hasLocationPermissions
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: FragmentMapsBinding

    private var routePolylines = mutableListOf<ListOfLocations>()
    private var service = NavigationMapTrackService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        requestPermissions()
        binding.mapView.onCreate(savedInstanceState)
        setupService()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToggle.setOnClickListener {
            toggleButtonText()
            sendCommandToService(START_OR_RESUME_SERVICE_ACTION)
        }

        binding.mapView.getMapAsync { map ->
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }

    }

    private fun toggleButtonText() {
        service.isTracking.observe(viewLifecycleOwner) { isTracking ->
            setTextAndClickListener(isTracking)
        }
    }

    private fun setTextAndClickListener(isTracking: Boolean) {
        val buttonText = if (isTracking) getString(R.string.pause) else getString(R.string.restart)
        binding.btnToggle.text = buttonText
        binding.btnToggle.setOnClickListener {
            if (isTracking) {
                sendCommandToService(STOP_SERVICE_ACTION)
                binding.btnFinish.visibility = View.VISIBLE
            } else {
                sendCommandToService(START_OR_RESUME_SERVICE_ACTION)
                binding.btnFinish.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupService() {
        service.listOfPolylinesLiveData.observe(viewLifecycleOwner) {
            routePolylines = it
            moveCameraToUserLocationWithZoom(routePolylines)
        }
    }

    private fun moveCameraToUserLocationWithZoom(routePolylines: MutableList<MutableList<LatLng>>) {
        if (routePolylines.isNotEmpty() && routePolylines.last().isNotEmpty()) {
            binding.mapView.getMapAsync { map ->
                with(map) {
                    animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            routePolylines.last().last(),
                            GOOGLE_MAPS_CAMERA_ZOOM_VALUE
                        )
                    )
                }
            }
        }
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), NavigationMapTrackService::class.java).also { intent ->
            intent.action = action
            requireContext().startService(intent)
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