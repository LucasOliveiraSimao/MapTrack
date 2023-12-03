package com.lucassimao.maptrack.ui.map

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.data.entity.RouteEntity
import com.lucassimao.maptrack.databinding.FragmentMapsBinding
import com.lucassimao.maptrack.service.MapTrackService
import com.lucassimao.maptrack.ui.home.RouteViewModel
import com.lucassimao.maptrack.util.Constants
import com.lucassimao.maptrack.util.Constants.GOOGLE_MAPS_CAMERA_ZOOM_VALUE
import com.lucassimao.maptrack.util.Constants.PAUSE_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.PERMISSION_REQUEST_CODE
import com.lucassimao.maptrack.util.Constants.START_OR_RESUME_SERVICE_ACTION
import com.lucassimao.maptrack.util.ListOfLocations
import com.lucassimao.maptrack.util.PermissionUtil
import com.lucassimao.maptrack.util.buildPolylineOption
import com.lucassimao.maptrack.util.calculateRouteDistance
import com.lucassimao.maptrack.util.formatFloatToTwoDecimalPlaces
import com.lucassimao.maptrack.util.getFormattedElapsedTime
import com.lucassimao.maptrack.util.metersToKilometers
import com.lucassimao.maptrack.util.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.Calendar

@AndroidEntryPoint
class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: FragmentMapsBinding

    private val viewModel by viewModels<RouteViewModel>()
    private val speedAverageViewModel by viewModels<SpeedAverageViewModel>()

    private var routePolylines = mutableListOf<ListOfLocations>()
    private var service = MapTrackService
    private var map: GoogleMap? = null
    private var totalExecutionTime = 0L
    private var averageSpeed = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        requestPermissions()

        binding.mapView.getMapAsync { googleMaps ->
            map = googleMaps
            configureMapStyle(googleMaps)
        }

        binding.btnToggle.setOnClickListener {
            toggleButtonText()
            sendCommandToService(START_OR_RESUME_SERVICE_ACTION)
        }

        binding.btnFinish.setOnClickListener {
            centerCameraOnUser()
            finalizeAndSaveRunData()
        }

        setupService()

        binding.mapView.onCreate(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (PermissionUtil.hasLocationPermissions(requireContext())) {
            return
        } else {
            showPermissionDialog(
                positiveAction = {
                    requestPermissions()
                },
                negativeAction = {
                    binding.btnToggle.isEnabled = false
                }
            )
        }
    }

    private fun observeAverageSpeed() {
        speedAverageViewModel.getSpeedAverage(calculateTotalDistance(), totalExecutionTime)
            .observe(viewLifecycleOwner) {
                averageSpeed = it
            }
    }

    private fun configureMapStyle(googleMaps: GoogleMap) {
        googleMaps.setMapStyle(loadMapStyle())
    }

    private fun loadMapStyle(): MapStyleOptions {
        val styleResourceId = R.raw.style_map
        return MapStyleOptions.loadRawResourceStyle(requireContext(), styleResourceId)
    }

    private fun finalizeAndSaveRunData() {
        var distanceTraveledInMeters = 0.0f
        val currentTimeInMillis = Calendar.getInstance().timeInMillis

        for (route in routePolylines) {
            distanceTraveledInMeters += calculateRouteDistance(route)
        }

        speedAverageViewModel.getSpeedAverage(distanceTraveledInMeters, totalExecutionTime)
            .observe(viewLifecycleOwner) {
                averageSpeed = it
            }

        val distanceTraveledInKM = metersToKilometers(distanceTraveledInMeters).toDouble()

        map?.snapshot { bitmap ->
            val route = RouteEntity(
                bitmap,
                currentTimeInMillis,
                averageSpeed,
                distanceTraveledInKM,
                totalExecutionTime
            )

            viewModel.insertRoute(route)

            finalizeRun()

        }

    }

    private fun displayDistanceTraveled(): String {
        var distanceTraveledInMeters = 0.0f

        for (route in routePolylines) {
            distanceTraveledInMeters += calculateRouteDistance(route)
        }

        val distanceTraveledInKm = metersToKilometers(distanceTraveledInMeters)

        return formatFloatToTwoDecimalPlaces(distanceTraveledInKm)
    }

    private fun calculateTotalDistance(): Float {
        var totalDistance = 0.0f

        for (route in routePolylines) {
            totalDistance += calculateRouteDistance(route)
        }

        return totalDistance
    }

    private fun displayAverageSpeed(): String {
        observeAverageSpeed()

        return formatFloatToTwoDecimalPlaces(averageSpeed)
    }

    private fun finalizeRun() {
        sendCommandToService(Constants.STOP_SERVICE_ACTION)
        findNavController().navigate(R.id.action_mapsFragment_to_homeFragment)
    }

    private fun toggleButtonText() {
        service.isTrackingLiveData.observe(viewLifecycleOwner) { isTracking ->
            setTextAndClickListener(isTracking)
        }
    }

    private fun setTextAndClickListener(isTracking: Boolean) {
        val buttonText = if (isTracking) getString(R.string.pause) else getString(R.string.restart)
        binding.btnToggle.text = buttonText
        binding.btnToggle.setOnClickListener {
            if (isTracking) {
                sendCommandToService(PAUSE_SERVICE_ACTION)
                binding.btnFinish.visibility = View.VISIBLE
            } else {
                sendCommandToService(START_OR_RESUME_SERVICE_ACTION)
                binding.btnFinish.visibility = View.GONE
            }
        }
    }

    private fun setupService() {
        service.listOfPolylinesLiveData.observe(viewLifecycleOwner) {
            routePolylines = it
            binding.distanceTraveled.text =
                getString(R.string.distance_traveled_format, displayDistanceTraveled())
            addAllPolylines()
            addLastedPolyline()
            moveCameraToUserLocationWithZoom()
        }

        service.totalExecutionTimeLiveData.observe(viewLifecycleOwner) {
            totalExecutionTime = it
            binding.averageSpeed.text =
                getString(R.string.average_speed_format, displayAverageSpeed())
            binding.timeCounter.text = getFormattedElapsedTime(it)
        }
    }

    private fun moveCameraToUserLocationWithZoom() {
        if (routePolylines.isNotEmpty() && routePolylines.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    routePolylines.last().last(),
                    GOOGLE_MAPS_CAMERA_ZOOM_VALUE
                )
            )
        }
    }

    private fun centerCameraOnUser() {

        val mapWidth = binding.mapView.width
        val mapHeight = binding.mapView.height
        val mapPadding = (binding.mapView.height * 0.05f).toInt()

        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                getRouteBounds(routePolylines),
                mapWidth,
                mapHeight,
                mapPadding
            )
        )
    }

    private fun getRouteBounds(routePolylines: MutableList<ListOfLocations>): LatLngBounds {
        val boundsBuilder = LatLngBounds.Builder()

        for (polyline in routePolylines) {
            for (point in polyline) {
                boundsBuilder.include(point)
            }
        }

        return boundsBuilder.build()
    }

    private fun addLastedPolyline() {
        if (routePolylines.isNotEmpty() && routePolylines.last().size > 1) {

            val preLastPosition = routePolylines.last()[routePolylines.last().size - 2]
            val lastPosition = routePolylines.last().last()

            val polylineOptions = buildPolylineOption(preLastPosition, lastPosition)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun addAllPolylines() {
        for (route in routePolylines) {

            val polylineOptions = buildPolylineOption(route)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), MapTrackService::class.java).also { intent ->
            intent.action = action
            requireContext().startService(intent)
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.android_13_permission_message),
                PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
            )

        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.you_need_to_accept_location_permissions_to_use_this_app),
                PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

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