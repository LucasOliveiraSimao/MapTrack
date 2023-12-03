package com.lucassimao.maptrack.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.data.entity.RouteEntity
import com.lucassimao.maptrack.databinding.ListItemBinding
import com.lucassimao.maptrack.util.formatDate
import com.lucassimao.maptrack.util.formatFloatToTwoDecimalPlaces
import com.lucassimao.maptrack.util.getFormattedElapsedTime

class RouteAdapter(private val listOfRoutes: List<RouteEntity>) :
    RecyclerView.Adapter<RouteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return RouteViewHolder(binding)
    }

    override fun getItemCount() = listOfRoutes.size

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(listOfRoutes[position])
    }
}

class RouteViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(route: RouteEntity) {
        binding.apply {

            val distanceFormatFloat = formatFloatToTwoDecimalPlaces(route.distanceTraveledInKM.toFloat())
            val averageSpeedFormatFloat = formatFloatToTwoDecimalPlaces(route.averageSpeed)
            val timeFormat = getFormattedElapsedTime(route.totalExecutionTime)

            val distanceString = itemView.resources.getString(
                R.string.distance_format,
                distanceFormatFloat
            )
            val averageSpeedString = itemView.resources.getString(
                R.string.average_speed_format,
                averageSpeedFormatFloat
            )
            val timeString = itemView.resources.getString(
                R.string.time_format,
                timeFormat
            )

            Glide.with(binding.root).load(route.image).into(listItemImage)

            listItemDistance.text = distanceString

            listItemDate.text = formatDate(route.currentDateInMillis)

            listItemAverageSpeed.text = averageSpeedString

            listItemTime.text = timeString
        }
    }

}