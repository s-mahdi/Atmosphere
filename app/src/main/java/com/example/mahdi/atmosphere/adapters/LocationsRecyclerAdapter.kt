package com.example.mahdi.atmosphere.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mahdi.atmosphere.R
import com.example.mahdi.atmosphere.models.opencage.Result
import java.util.*

class LocationsRecyclerAdapter(context: Context, private val locationArrayList: ArrayList<Result>,
                               val itemClickListener: ItemClickListener) : RecyclerView.Adapter<LocationsRecyclerAdapter.LocationsViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        return LocationsViewHolder(inflater.inflate(R.layout.list_location_row, parent, false))
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val location = locationArrayList[position]
        holder.cityTextView.text = String.format(location.formatted)
    }

    inner class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val cityTextView: TextView = itemView.findViewById(R.id.rowCityTextView)
        override fun onClick(view: View) {
            itemClickListener.onItemClickListener(view, layoutPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun getItem(position: Int): Result {
        return locationArrayList[position]
    }

    interface ItemClickListener {
        fun onItemClickListener(view: View?, position: Int)
    }

    override fun getItemCount(): Int {
        return locationArrayList.size
    }
}