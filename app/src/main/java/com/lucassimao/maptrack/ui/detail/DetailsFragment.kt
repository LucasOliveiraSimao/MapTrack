package com.lucassimao.maptrack.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.commom.formatFloatToTwoDecimalPlaces
import com.lucassimao.maptrack.data.entity.RouteEntity
import com.lucassimao.maptrack.databinding.FragmentDetailsBinding
import com.lucassimao.maptrack.util.formatDate
import com.lucassimao.maptrack.util.getFormattedElapsedTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : DialogFragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val route: RouteEntity? = arguments?.getParcelable(KEY)

        route?.let {
            setupDetailsViews(it)
        }
    }

    private fun setupDetailsViews(route: RouteEntity) {
        binding.apply {
            detailsImageRoute.setImageBitmap(route.image)
            detailsDate.text = formatDate(route.currentDateInMillis)
            detailsDistance.text = getString(
                R.string.total_distance_format,
                formatFloatToTwoDecimalPlaces(route.distanceTraveledInKM.toFloat())
            )
            detailsTime.text = getFormattedElapsedTime(route.totalExecutionTime)
            detailsAvgSpeed.text = getString(
                R.string.maps_average_speed_format,
                formatFloatToTwoDecimalPlaces(route.averageSpeed)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        const val KEY = "key"
    }
}