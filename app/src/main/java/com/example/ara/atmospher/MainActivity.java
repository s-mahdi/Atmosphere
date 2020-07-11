package com.example.ara.atmospher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.example.ara.atmospher.functions.Background;
import com.example.ara.atmospher.functions.Condition;
import com.example.ara.atmospher.functions.Icon;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ara.atmospher.Interfaces.OpenWeatherService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String API_KEY = "ece8f3c084bf15aef779da23422b4aab";
    private static final String TAG = "TEST_LOG";
    WeatherData weatherData;
    NavigationView navView;
    // TODO: 17/01/2019 use Reverse geocoding to get persian name
    private String CITY_NAME = "tehran";
    private TextView cityNameTextView;
    private TextView tempTextView;
    private TextView maxTempTextView;
    private TextView minTempTextView;
    private TextView climateConditionTextView;
    private ImageView climateConditionImageView;
    private ImageButton addCityImageButton;
    private ImageButton searchCityImageButton;
    private ImageButton closeSearchPlotImageButton;
    private ImageButton drawerHamburgerImageButton;
    private ImageView backGroundImageView;
    private View searchPlot;
    private EditText searchCityEditText;
    private DrawerLayout mDrawerLayout;

    private Icon icon;
    private Condition condition;
    private Background background;

    private OpenWeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        getViews();

        setListeners();

        configureNavigationDrawer();

        icon = new Icon();
        condition = new Condition();
        background = new Background();

        weatherData = new WeatherData();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        service = retrofit.create(OpenWeatherService.class);

        setWeather();


    }

    @Override
    public void onClick(View view) {
        //uses for opening and closing soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.imageButton_addCity:
                searchPlot.setVisibility(View.VISIBLE);
                //show soft keyboard
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                break;
            case R.id.search_city_button:
                if (!searchCityEditText.getText().toString().equals("")) {
                    CITY_NAME = searchCityEditText.getText().toString();
                    searchPlot.setVisibility(View.INVISIBLE);
                    setWeather();

                    //hide soft keyboard
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    searchCityEditText.setText("");
                } else Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ImageButton_close_search_plot:
                searchPlot.setVisibility(View.INVISIBLE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                break;

            case R.id.imageButton_drawer_hamburger:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;


        }
    }

    private void configureNavigationDrawer() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment f = null;
                int itemId = menuItem.getItemId();
                if (itemId == R.id.temperatureUnit) {
                    // TODO: 19/01/2019 add some fragments to this
                    Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.locations) {
                    Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                }
                if (f != null) {
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

    private void setWeather() {
        Dexter.withActivity(MainActivity.this)//get and set permissions
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new BasePermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        super.onPermissionGranted(response);

                        Call<WeatherData> call = service.weatherCall(CITY_NAME, API_KEY);

                        call.enqueue(new Callback<WeatherData>() {
                            @Override
                            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                                weatherData = response.body();
                                Log.i(TAG, "onResponse: " + String.valueOf(response.body()));
                                if (response.body() != null) {
                                    setView();
                                } else {
                                    // TODO: 03/02/2019 set to works with response.errorBody
                                    Toast.makeText(MainActivity.this, "شهر یافت نشد", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WeatherData> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "اتصال با سرور برقرار نیست", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        super.onPermissionDenied(response);
                        PermissionListener dialogPermissionListener = DialogOnDeniedPermissionListener.Builder
                                .withContext(MainActivity.this)
                                .withTitle("Internet Permission")
                                .withMessage("We Need Internet to Get the WeatherData Data")
                                .withButtonText("Ok")
                                .build();
                    }
                }).check();
    }

    private void setListeners() {
        addCityImageButton.setOnClickListener(this);
        searchCityImageButton.setOnClickListener(this);
        closeSearchPlotImageButton.setOnClickListener(this);
        drawerHamburgerImageButton.setOnClickListener(this);
    }

    private void getViews() {
        cityNameTextView = findViewById(R.id.textView_cityName);
        tempTextView = findViewById(R.id.textView_temperature);
        maxTempTextView = findViewById(R.id.textView_maxTemp);
        minTempTextView = findViewById(R.id.textView_minTemp);
        climateConditionTextView = findViewById(R.id.textView_climateCondition);
        climateConditionImageView = findViewById(R.id.imageView_climateCondition);
        addCityImageButton = findViewById(R.id.imageButton_addCity);
        searchPlot = findViewById(R.id.searchPlot);
        searchCityImageButton = findViewById(R.id.search_city_button);
        searchCityEditText = findViewById(R.id.editText_search_city);
        closeSearchPlotImageButton = findViewById(R.id.ImageButton_close_search_plot);
        drawerHamburgerImageButton = findViewById(R.id.imageButton_drawer_hamburger);
        backGroundImageView = findViewById(R.id.imageView_background);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.navigation);
    }

    @SuppressLint("SetTextI18n")
    public void setView() {
        int id = weatherData.getWeatherList().get(0).getId();

        mDrawerLayout.setVisibility(View.VISIBLE);
        cityNameTextView.setText(weatherData.getCityName());
        tempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemperature() - 273) + "°");
        maxTempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemp_max() - 273));
        minTempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemp_min() - 273));

        climateConditionTextView.setText(condition.getCondition(id));
        climateConditionImageView.setImageDrawable(getDrawable(icon.getIcon(id)));
        backGroundImageView.setImageDrawable(getDrawable(background.getBackground(id)));
    }

}
