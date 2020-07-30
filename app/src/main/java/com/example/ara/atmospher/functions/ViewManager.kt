package com.example.ara.atmospher.functions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ara.atmospher.R
import com.example.ara.atmospher.adapters.ForecastRecyclerAdapter
import com.example.ara.atmospher.models.openWeather.oneCall.Daily
import com.example.ara.atmospher.models.openWeather.oneCall.OneCall
import com.example.ara.atmospher.models.opencage.Result
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
//    private val forecastTable: LinearLayout = activity.findViewById(R.id.forecastTable)
//    private val forecastRow: ConstraintLayout = activity.findViewById(R.id.forecastRow)

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
        temp.text = current.temp.roundToInt().toString().plus("°")
        minTemp.text = today.temp.min.roundToInt().toString().plus("°")
        maxTemp.text = today.temp.max.roundToInt().toString().plus("°")
        condition.text = getCondition(id)
        icon.setImageDrawable(activity.getDrawable(getIcon(id)))
    }

    fun setSlider() {
        val item1 = PrimaryDrawerItem()
        item1.identifier = 1
        item1.name = StringHolder(R.string.settings)
        item1.icon = ImageHolder(R.drawable.ic_settings)

        val item2 = PrimaryDrawerItem()
        item2.identifier = 2
        item2.name = StringHolder(R.string.temperature_unit)
        item2.icon = ImageHolder(R.drawable.ic_thermometer)

        slider.itemAdapter.add(
                item1,
                item2
        )

        slider.onDrawerItemClickListener = { v, drawerItem, position ->
            Toast.makeText(activity, "position is $position", Toast.LENGTH_SHORT).show()
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
