package com.example.mahdi.atmosphere.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mahdi.atmosphere.R
import com.example.mahdi.atmosphere.events.ClickManager
import com.example.mahdi.atmosphere.events.KeyManager
import com.example.mahdi.atmosphere.functions.*
import com.example.mahdi.atmosphere.models.opencage.Geometry
import com.example.mahdi.atmosphere.viewModels.MainViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private var climateConditionImageView: ImageView? = null
    private var backGroundImageView: ImageView? = null
    private var addCityImageButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var closeSearchPlotImageButton: ImageButton? = null
    private var drawerHamburgerImageButton: ImageButton? = null
    private var searchBar: View? = null
    private var searchInput // used for city name
            : EditText? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var viewManager: ViewManager? = null
    private var clickManager: ClickManager? = null
    private var keyManager: KeyManager? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)

        if (checkFirstTime(this)) {
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean("isFirstTime", false).apply()
            startActivity(Intent(this@MainActivity, Introduction::class.java))
            finish()
        }

        //hide status bar
        hideStatusBar(this)
        setContentView(R.layout.activity_main)
        initiate()

        viewManager?.setSlider()

        setEvents()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.oneCallData.observe(this, Observer {
            if (it != null) {
                viewManager?.setWeatherView(it)
                viewManager?.setForecastView(it)
            }
        })

        viewModel.citiesData.observe(this, Observer {
            if (it != null && it.results.isNotEmpty()) {
                val locations = filterLocations(it.results, "city", "village")
                if (locations.size == 1) {
                    val location = locations[0]
                    val locationName = getLocationName(location)
                    viewManager?.setCityView(locationName)
                    viewModel.setCityGeometry(location.geometry)
                    val map: Map<String, Geometry> = mapOf(locationName to locations[0].geometry)
                    updatePreferences(this@MainActivity, "city", Gson().toJson(map))
                } else if (locations.size > 1) launchLocationPickerDialog(this, locations)
            } else Toast.makeText(this@MainActivity, "آخ! پیدا نشد :(", Toast.LENGTH_SHORT).show()
        })

        val city = syncPreferences(this)
        if (city != null) {
            viewModel.setCityGeometry(city.values.first())
            viewManager?.setCityView(city.keys.first())
        } else viewModel.setCityName("تهران")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

    private fun setEvents() {
        addCityImageButton?.setOnClickListener(clickManager)
        searchButton?.setOnClickListener(clickManager)
        closeSearchPlotImageButton?.setOnClickListener(clickManager)
        drawerHamburgerImageButton?.setOnClickListener(clickManager)
        backGroundImageView?.setOnClickListener(clickManager)
        searchInput?.setOnKeyListener(KeyManager(this) {
            onSearch(this) {
                viewModel.setCityName(searchInput?.text.toString())
            }
        })
        searchInput?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchBar?.visibility = View.INVISIBLE
            }
        }
    }

    private fun initiate() {
        // set views
        climateConditionImageView = findViewById(R.id.imageView_climateCondition)
        addCityImageButton = findViewById(R.id.imageButton_addCity)
        searchBar = findViewById(R.id.searchbar)
        searchButton = findViewById(R.id.search_city_button)
        searchInput = findViewById(R.id.editText_search_city)
        closeSearchPlotImageButton = findViewById(R.id.ImageButton_close_search_plot)
        drawerHamburgerImageButton = findViewById(R.id.imageButton_drawer_hamburger)
        backGroundImageView = findViewById(R.id.imageView_background)
        mDrawerLayout = findViewById(R.id.layout)
        viewManager = ViewManager(this)

        clickManager = ClickManager(this) {
            onSearch(this) {
                viewModel.setCityName(searchInput?.text.toString())
            }
        }
        keyManager = KeyManager(this) {
            onSearch(this) {
                viewModel.setCityName(searchInput?.text.toString())
            }
        }
    }

}