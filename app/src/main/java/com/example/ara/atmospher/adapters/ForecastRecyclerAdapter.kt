package com.example.ara.atmospher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ara.atmospher.R
import com.example.ara.atmospher.functions.getIcon
import com.example.ara.atmospher.functions.getTempByUnit
import com.example.ara.atmospher.functions.getWeekDay
import com.example.ara.atmospher.models.openWeather.oneCall.Daily
import java.util.*
import kotlin.math.roundToInt

class ForecastRecyclerAdapter(private val context: Context,
                              private val weatherArrayList: ArrayList<Daily>
) : RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastTableViewHolder>() {

    private val isImperial = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("unit", false)
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastTableViewHolder {
        return ForecastTableViewHolder(inflater.inflate(R.layout.forecast_row, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastTableViewHolder, position: Int) {
        val forecast: Daily = weatherArrayList[position]
        val weather = forecast.weatherList[0]
        holder.day.text = getWeekDay(forecast.dt)
        holder.icon.setImageDrawable(context.getDrawable(getIcon(weather.id)))
        holder.maxTemp.text = getTempByUnit(forecast.temp.max.roundToInt(), isImperial).toString().plus("°")
        holder.minTemp.text = getTempByUnit(forecast.temp.min.roundToInt(), isImperial).toString().plus("°")
    }

    override fun getItemCount(): Int {
        return weatherArrayList.size
    }

    inner class ForecastTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.forecastDay)
        val icon: ImageView = itemView.findViewById(R.id.forecastIcon)
        val maxTemp: TextView = itemView.findViewById(R.id.forecastMax)
        val minTemp: TextView = itemView.findViewById(R.id.forecastMin)
    }
}