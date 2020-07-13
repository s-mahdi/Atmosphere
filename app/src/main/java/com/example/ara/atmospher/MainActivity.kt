package com.example.ara.atmospher

import android.Manifest
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
import com.example.ara.atmospher.MainActivity
import com.example.ara.atmospher.events.ClickManager
import com.example.ara.atmospher.functions.*
import com.example.ara.atmospher.retrofit.OpenWeatherService
import com.example.ara.atmospher.retrofit.RetrofitClient.instance
import com.google.android.material.navigation.NavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var weatherData: WeatherData? = null
    var navView: NavigationView? = null

    // TODO: 17/01/2019 use Reverse geocoding to get persian name
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
    private var background: Background? = null
    private var service: OpenWeatherService? = null
    private var imm: InputMethodManager? = null
    private var viewManager: ViewManager? = null
    private var sharedPref: SharedPreferences? = null
    private var clickManager: ClickManager? = null
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
        CITY_NAME = syncStorage(sharedPref!!)
        configureNavigationDrawer()
        service = instance
        handleRequest(CITY_NAME)
    }

    private fun onSearch() {
        val searchValue = searchInput!!.text.toString()
        if (!searchValue.isEmpty()) {
            viewManager!!.toggleView(searchBar)
            handleRequest(searchValue)

            //hide soft keyboard
            viewManager!!.toggleSoftKeyboard(imm)
            searchInput!!.setText("")
        } else Toast.makeText(this, "متنی وارد نشده!", Toast.LENGTH_SHORT).show()
        return
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
                mDrawerLayout!!.closeDrawers()
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    private fun handleRequest(cityName: String?) {
        Dexter.withActivity(this@MainActivity) //get and set permissions
                .withPermission(Manifest.permission.INTERNET)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        super.onPermissionGranted(response)
                        val call = service!!.weatherCall(cityName, API_KEY)
                        call.enqueue(object : Callback<WeatherData?> {
                            override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
                                weatherData = response.body()
                                if (response.body() != null) {
                                    setView()
                                    CITY_NAME = syncStorage(sharedPref!!)
                                } else {
                                    // TODO: 03/02/2019 set to works with response.errorBody
                                    Toast.makeText(this@MainActivity, "شهر یافت نشد", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                                Toast.makeText(this@MainActivity, "اتصال با سرور برقرار نیست", Toast.LENGTH_LONG).show()
                            }
                        })
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        super.onPermissionDenied(response)
                        val dialogPermissionListener: PermissionListener = DialogOnDeniedPermissionListener.Builder
                                .withContext(this@MainActivity)
                                .withTitle("Internet Permission")
                                .withMessage("We Need Internet to Get the WeatherData Data")
                                .withButtonText("Ok")
                                .build()
                    }
                }).check()
    }

    private fun setEvents() {
        addCityImageButton!!.setOnClickListener(clickManager)
        searchButton!!.setOnClickListener(clickManager)
        closeSearchPlotImageButton!!.setOnClickListener(clickManager)
        drawerHamburgerImageButton!!.setOnClickListener(clickManager)
        backGroundImageView!!.setOnClickListener(clickManager)
        searchInput!!.setOnKeyListener(View.OnKeyListener { view, i, event ->
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                viewManager!!.toggleView(searchBar)
                viewManager!!.toggleSoftKeyboard(imm)
                handleRequest(searchInput!!.text.toString())
                searchInput!!.setText("")
                return@OnKeyListener true
            }
            false
        })
        searchInput!!.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
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
        background = Background()
        weatherData = WeatherData()
        viewManager = ViewManager()
        sharedPref = getSharedPreferences(
                getString(R.string.city_key), Context.MODE_PRIVATE)
        clickManager = ClickManager(this) { onSearch() }
    }

    @SuppressLint("SetTextI18n")
    fun setView() {
        val id = weatherData!!.weatherList[0].getId()
        imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        mDrawerLayout!!.visibility = View.VISIBLE
        cityNameTextView!!.text = weatherData!!.cityName
        tempTextView!!.text = (weatherData!!.mainCondition.getTemperature().toInt() - 273).toString() + "°"
        maxTempTextView!!.text = (weatherData!!.mainCondition.getTemp_max().toInt() - 273).toString()
        minTempTextView!!.text = (weatherData!!.mainCondition.getTemp_min().toInt() - 273).toString()
        climateConditionTextView!!.text = condition!!.getCondition(id)
        climateConditionImageView!!.setImageDrawable(getDrawable(icon!!.getIcon(id)))
        backGroundImageView!!.setImageDrawable(getDrawable(background!!.getBackground(id)))
    }

    companion object {
        private const val API_KEY = "ece8f3c084bf15aef779da23422b4aab"
        private const val TAG = "TEST_LOG"
    }
}