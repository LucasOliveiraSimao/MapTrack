package com.lucassimao.maptrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.lucassimao.maptrack.NavGraphDirections
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.commom.formatFloatToTwoDecimalPlaces
import com.lucassimao.maptrack.data.entity.RouteEntity
import com.lucassimao.maptrack.databinding.FragmentHomeBinding
import com.lucassimao.maptrack.ui.detail.DetailsFragment
import com.lucassimao.maptrack.util.getFormattedElapsedTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var routeAdapter: RouteAdapter

    private val viewModel by viewModels<RouteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.fabNewRoute.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
        }

    }

    private fun initViewModel() {
        viewModel.getAllRoutes.observe(viewLifecycleOwner) {
            if (it != null) {
                initRecyclerView(it)
            }
        }

        viewModel.getTotalDistance.observe(viewLifecycleOwner) {
            if (it != null) {
                val distanceFormat = formatFloatToTwoDecimalPlaces(it.toFloat())
                val distanceString = getString(
                    R.string.total_distance_format,
                    distanceFormat
                )
                binding.includeCardHome.contentCardTotalKm.text = distanceString
            }
        }

        viewModel.getTotalExecutionTime.observe(viewLifecycleOwner) {
            if (it != null) {
                val timeFormat = getFormattedElapsedTime(it)
                binding.includeCardHome.contentCardTotalTime.text = timeFormat
            }
        }
    }

    private fun initRecyclerView(routes: List<RouteEntity>) {
        val onItemClick: (RouteEntity) -> Unit = { item ->
            val action = NavGraphDirections.actionOpenDialog().apply {
                arguments.putParcelable(DetailsFragment.KEY, item)
            }
            navigateTo(action)
        }

        setupRouteAdapter(routes, onItemClick)
    }

    private fun setupRouteAdapter(routes: List<RouteEntity>, onItemClick: (RouteEntity) -> Unit) {
        routeAdapter = RouteAdapter(routes, onItemClick)
        binding.homeListOfRoutes.adapter = routeAdapter
    }

    private fun navigateTo(action: NavDirections) {
        findNavController().navigate(action)
    }
}