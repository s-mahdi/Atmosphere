package com.example.ara.atmospher.functions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ara.atmospher.R
import com.example.ara.atmospher.model.WeatherData
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.withIdentifier
import com.mikepenz.materialdrawer.model.interfaces.withName
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView

class ViewManager {
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

    fun setWeatherView(weatherData: WeatherData, activity: Activity) {
        val bg: ImageView = activity.findViewById(R.id.imageView_background)
        val city: TextView = activity.findViewById(R.id.textView_cityName)
        val temp: TextView = activity.findViewById(R.id.textView_temperature)
        val minTemp: TextView = activity.findViewById(R.id.textView_minTemp)
        val maxTemp: TextView = activity.findViewById(R.id.textView_maxTemp)
        val condition: TextView = activity.findViewById(R.id.textView_climateCondition)
        val icon: ImageView = activity.findViewById(R.id.imageView_climateCondition)
        val layout: DrawerLayout = activity.findViewById(R.id.drawer_layout)

        layout.visibility = View.VISIBLE
        val id = weatherData.weatherList[0].id
        bg.setImageDrawable(activity.getDrawable(getBackground(id)))
        city.text = weatherData.cityName
        temp.text = (weatherData.mainCondition.temperature.toInt() - 273).toString().plus("°")
        minTemp.text = (weatherData.mainCondition.temp_min.toInt() - 273).toString().plus("°")
        maxTemp.text = (weatherData.mainCondition.temp_max.toInt() - 273).toString().plus("°")
        condition.text = getCondition(id)
        icon.setImageDrawable(activity.getDrawable(getIcon(id)))


    }

    fun setSlider(activity: Activity) {
        val slider: MaterialDrawerSliderView = activity.findViewById(R.id.slider)


        val item1 = PrimaryDrawerItem()
        item1.identifier = 1;
        item1.name = StringHolder(R.string.settings)
        item1.icon = ImageHolder(R.drawable.ic_settings)

        val item2 = PrimaryDrawerItem()
        item2.identifier = 2;
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

}