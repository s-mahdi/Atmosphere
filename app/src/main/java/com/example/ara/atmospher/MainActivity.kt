package com.example.ara.atmospher

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ara.atmospher.events.ClickManager
import com.example.ara.atmospher.functions.*
import com.example.ara.atmospher.model.WeatherData
import com.example.ara.atmospher.viewModels.MainViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private var weatherData: WeatherData? = null
    var navView: NavigationView? = null

    private var CITY_NAME: String? = null
    private var cityNameTextView: TextView? = null
    private var tempTextView: TextView? = null
    private var maxTempTextView: TextView? = null
    private var minTempTextView: TextView? = null
    private var climateConditionTextView: TextView? = null
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
    private var icon: Icon? = null
    private var condition: Condition? = null
    private var imm: InputMethodManager? = null
    private var viewManager: ViewManager? = null
    private var sharedPref: SharedPreferences? = null
    private var clickManager: ClickManager? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)

        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        initializer()

        setEvents()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.weatherDate.observe(this, Observer {setView(it)})

        viewModel.setCityName("yazd")

        CITY_NAME = syncStorage(sharedPref!!)
        configureNavigationDrawer()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

    private fun configureNavigationDrawer() {
        navView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val f: Fragment? = null
            val itemId = menuItem.itemId
            if (itemId == R.id.temperatureUnit) {
                // TODO: 19/01/2019 add some fragments to this
                Toast.makeText(this@MainActivity, "Clicked", Toast.LENGTH_SHORT).show()
            } else if (itemId == R.id.locations) {
                Toast.makeText(this@MainActivity, "Clicked", Toast.LENGTH_SHORT).show()
            }
            if (f != null) {
                mDrawerLayout?.closeDrawers()
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    private fun setEvents() {
        addCityImageButton!!.setOnClickListener(clickManager)
        searchButton!!.setOnClickListener(clickManager)
        closeSearchPlotImageButton!!.setOnClickListener(clickManager)
        drawerHamburgerImageButton!!.setOnClickListener(clickManager)
        backGroundImageView!!.setOnClickListener(clickManager)
        searchInput!!.setOnKeyListener(View.OnKeyListener { _, _, event ->
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                viewManager!!.toggleView(searchBar!!)
                viewManager!!.toggleSoftKeyboard(imm!!)
//                handleRequest(searchInput!!.text.toString())
                searchInput!!.setText("")

                viewModel.setCityName(searchInput?.text.toString())
                return@OnKeyListener true
            }
            false
        })
        searchInput!!.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchBar!!.visibility = View.INVISIBLE
            }
        }
    }

    private fun initializer() {
        // set views
        cityNameTextView = findViewById(R.id.textView_cityName)
        tempTextView = findViewById(R.id.textView_temperature)
        maxTempTextView = findViewById(R.id.textView_maxTemp)
        minTempTextView = findViewById(R.id.textView_minTemp)
        climateConditionTextView = findViewById(R.id.textView_climateCondition)
        climateConditionImageView = findViewById(R.id.imageView_climateCondition)
        addCityImageButton = findViewById(R.id.imageButton_addCity)
        searchBar = findViewById(R.id.searchPlot)
        searchButton = findViewById(R.id.search_city_button)
        searchInput = findViewById(R.id.editText_search_city)
        closeSearchPlotImageButton = findViewById(R.id.ImageButton_close_search_plot)
        drawerHamburgerImageButton = findViewById(R.id.imageButton_drawer_hamburger)
        backGroundImageView = findViewById(R.id.imageView_background)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.navigation)
        icon = Icon()
        condition = Condition()
        viewManager = ViewManager()
        sharedPref = getSharedPreferences(
                getString(R.string.city_key), Context.MODE_PRIVATE)
        clickManager = ClickManager(this) {
            onSearch(this) {
                viewModel.setCityName(searchInput?.text.toString())
            }
        }
        imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @SuppressLint("SetTextI18n")
    fun setView(weatherData: WeatherData) {
        val id = weatherData.weatherList[0].id
        mDrawerLayout!!.visibility = View.VISIBLE
        cityNameTextView!!.text = weatherData.cityName
        tempTextView!!.text = (weatherData.mainCondition.temperature.toInt() - 273).toString() + "°"
        maxTempTextView!!.text = (weatherData.mainCondition.temp_max.toInt() - 273).toString()
        minTempTextView!!.text = (weatherData.mainCondition.temp_min.toInt() - 273).toString()
        climateConditionTextView!!.text = condition!!.getCondition(id)
        climateConditionImageView!!.setImageDrawable(getDrawable(icon!!.getIcon(id)))
        backGroundImageView!!.setImageDrawable(getDrawable(getBackground(id)))
    }
}