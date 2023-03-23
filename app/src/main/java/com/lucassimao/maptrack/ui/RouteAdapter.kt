package com.lucassimao.maptrack.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucassimao.maptrack.data.RouteEntity
import com.lucassimao.maptrack.databinding.ListItemBinding

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
            listItemImage.setImageResource(route.image)
            listItemDate.text = route.date
            listItemDistance.text = route.distance
            listItemTime.text = route.time
        }
    }

}