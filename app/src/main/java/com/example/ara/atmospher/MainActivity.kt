package com.example.ara.atmospher

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ara.atmospher.events.ClickManager
import com.example.ara.atmospher.events.KeyManager
import com.example.ara.atmospher.functions.ViewManager
import com.example.ara.atmospher.functions.hideStatusBar
import com.example.ara.atmospher.functions.onSearch
import com.example.ara.atmospher.functions.syncStorage
import com.example.ara.atmospher.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    //    var navView: NavigationView? = null
    private var CITY_NAME: String? = null
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
    private var sharedPref: SharedPreferences? = null
    private var clickManager: ClickManager? = null
    private var keyManager: KeyManager? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)

        //hide status bar
        hideStatusBar(this);
        setContentView(R.layout.activity_main)

        initializer()

        viewManager?.setSlider(this);

        setEvents()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.weatherDate.observe(this, Observer {
            viewManager?.setWeatherView(it, this)
        })

        viewModel.setCityName("yazd")

        CITY_NAME = syncStorage(sharedPref!!)
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

    private fun initializer() {
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
        viewManager = ViewManager()
        sharedPref = getSharedPreferences(
                getString(R.string.city_key), Context.MODE_PRIVATE)

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