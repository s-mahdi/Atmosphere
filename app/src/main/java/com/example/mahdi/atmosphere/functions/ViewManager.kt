package com.example.mahdi.atmosphere.functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mahdi.atmosphere.R
import com.example.mahdi.atmosphere.activities.SettingsActivity
import com.example.mahdi.atmosphere.adapters.ForecastRecyclerAdapter
import com.example.mahdi.atmosphere.models.openWeather.oneCall.Daily
import com.example.mahdi.atmosphere.models.openWeather.oneCall.OneCall
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import java.util.*
import kotlin.math.roundToInt

class ViewManager(private val activity: Activity) {
    private val bg: ImageView = activity.findViewById(R.id.imageView_background)
    private val city: TextView = activity.findViewById(R.id.textView_cityName)
    private val temp: TextView = activity.findViewById(R.id.temp)
    private val minTemp: TextView = activity.findViewById(R.id.min_temp)
    private val maxTemp: TextView = activity.findViewById(R.id.max_temp)
    private val condition: TextView = activity.findViewById(R.id.textView_climateCondition)
    private val icon: ImageView = activity.findViewById(R.id.imageView_climateCondition)
    private val wrapper: ConstraintLayout = activity.findViewById(R.id.viewWrapper)
    private val loaderLayout: ConstraintLayout = activity.findViewById(R.id.loaderLayout)
    private val backgroundWrapper: ConstraintLayout = activity.findViewById(R.id.backgroundWrapper)
    private val barsWrapper: ConstraintLayout = activity.findViewById(R.id.barsWrapper)
    private val slider: MaterialDrawerSliderView = activity.findViewById(R.id.slider)
    private val recyclerView: RecyclerView = activity.findViewById(R.id.forecastRecyclerView)
    private val feelsLike: TextView = activity.findViewById(R.id.feelsLike)
    private val humidity: TextView = activity.findViewById(R.id.humidity)
    private val visibility: TextView = activity.findViewById(R.id.visibility)
    private val uvIndex: TextView = activity.findViewById(R.id.uvIndex)
    private val detailIcon: ImageView = activity.findViewById(R.id.detailIcon)
    private val windSpeed: TextView = activity.findViewById(R.id.windSpeed)
    private val windDirection: TextView = activity.findViewById(R.id.windDirection)
    private val pressure: TextView = activity.findViewById(R.id.pressure)

    private val isImperial = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("unit", false)

    fun hideView(view: View) {
        view.visibility = View.INVISIBLE
    }

    fun showView(view: View) {
        view.visibility = View.VISIBLE
    }

    fun toggleSoftKeyboard(imm: InputMethodManager) {
        if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) else imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setWeatherView(oneCall: OneCall) {
        wrapper.visibility = View.VISIBLE
        backgroundWrapper.visibility = View.VISIBLE
        barsWrapper.visibility = View.VISIBLE
        loaderLayout.visibility = View.GONE

        val current = oneCall.current
        val today = oneCall.daily[0]

        val id = current.weatherList[0].id
        bg.setImageDrawable(activity.getDrawable(getBackground(id)))
        temp.text = getTempByUnit(current.temp.roundToInt(), isImperial).toString().plus("°")
        minTemp.text = getTempByUnit(today.temp.min.roundToInt(), isImperial).toString().plus("°")
        maxTemp.text = getTempByUnit(today.temp.max.roundToInt(), isImperial).toString().plus("°")
        condition.text = getCondition(id)
        icon.setImageDrawable(activity.getDrawable(getIcon(id)))

        feelsLike.text = getTempByUnit(current.feelsLike.roundToInt(), isImperial).toString().plus("°")
        humidity.text = current.humidity.toString().plus("%")
        visibility.text = (current.visibility / 1000).toString().plus(" کیلومتر")
        uvIndex.text = getUV(current.uvi.roundToInt())
        detailIcon.setImageDrawable(activity.getDrawable(getIcon(id)))

        windSpeed.text = current.windSpeed.toString().plus(" متر/ثانیه")
        windDirection.text = getWindDirection(current.windDegree)
        pressure.text = current.pressure.toString().plus(" هکتو پاسکال")
    }

    fun setSlider() {
        val item1 = PrimaryDrawerItem()
        item1.identifier = 1
        item1.name = StringHolder(R.string.settings)
        item1.icon = ImageHolder(R.drawable.ic_settings)

        slider.itemAdapter.add(
                item1
        )

        slider.onDrawerItemClickListener = { _, _, position ->
            when (position) {
                0 -> {
                    val intent = Intent(activity, SettingsActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }
            }
            false
        }

        slider.drawerLayout?.openDrawer(slider)
    }

    fun setForecastView(oneCall: OneCall) {
        val adapter = ForecastRecyclerAdapter(activity, oneCall.daily as ArrayList<Daily>)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    fun setCityView(cityName: String) {
        city.text = cityName
    }
}
