package com.lucassimao.maptrack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.data.RouteEntity
import com.lucassimao.maptrack.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var routeAdapter: RouteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(listOfRoutes())

    }

    private fun listOfRoutes(): List<RouteEntity> {
        return listOf(
            RouteEntity(R.drawable.img_track, "21/20/23", "13,00 km", "54 min"),
            RouteEntity(R.drawable.img_track, "20/20/23", "13,13 km", "50 min"),
            RouteEntity(R.drawable.img_track, "19/20/23", "12,87 km", "57 min"),
            RouteEntity(R.drawable.img_track, "18/20/23", "13,65 km", "52 min"),
            RouteEntity(R.drawable.img_track, "17/20/23", "13,01 km", "50 min"),
        )
    }

    private fun initRecyclerView(listOfRoutes: List<RouteEntity>) {
        routeAdapter = RouteAdapter(listOfRoutes)
        binding.homeListOfRoutes.adapter = routeAdapter
    }

}