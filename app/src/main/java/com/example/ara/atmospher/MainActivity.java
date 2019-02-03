package com.example.ara.atmospher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
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
    //faeze's chunks
    NavigationView navView;
    // TODO: 17/01/2019 use Reverse geocoding to get persian name
    private String CITY_NAME = "tesafasehran";
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
                mDrawerLayout.openDrawer(Gravity.END);
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
                                if (response.body() != null) {
                                    setView();
                                } else {
                                    // TODO: 03/02/2019 set to works with response.errorBody
                                    Toast.makeText(MainActivity.this, "city not found", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WeatherData> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Connecting field", Toast.LENGTH_LONG).show();
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

        mDrawerLayout.setVisibility(View.VISIBLE);
        cityNameTextView.setText(weatherData.getCityName());
        tempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemperature() - 273) + "°");
        maxTempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemp_max() - 273));
        minTempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemp_min() - 273));

        switch (weatherData.getWeatherList().get(0).getId() / 100) {
            case 2:
                backGroundImageView.setImageDrawable(getDrawable(R.drawable.thunderstorm));
                break;
            case 3:
                backGroundImageView.setImageDrawable(getDrawable(R.drawable.drizzle));
                break;
            case 5:
                backGroundImageView.setImageDrawable(getDrawable(R.drawable.rainy_3));
                break;
            case 6:
                backGroundImageView.setImageDrawable(getDrawable(R.drawable.snowy_2));
                break;
            case 7:
                backGroundImageView.setImageDrawable(getDrawable(R.drawable.foggy_2));
                break;
            case 8:
                backGroundImageView.setImageDrawable(getDrawable(R.drawable.sunny));
                break;
        }

        switch (weatherData.getWeatherList().get(0).getId()) {
            case 200:
                climateConditionTextView.setText("بارش خفیف همراه با رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 201:
                climateConditionTextView.setText("بارش  همراه با رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 202:
                climateConditionTextView.setText("بارش سنکین همراه با رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 210:
                climateConditionTextView.setText("رعد و برق خفیف");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 211:
                climateConditionTextView.setText("رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 212:
                climateConditionTextView.setText("رعد و برق شدید");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 221:
                climateConditionTextView.setText("رعد و برق پراکنده");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 230:
                climateConditionTextView.setText("بارش ریز خفیف همراه با رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 231:
                climateConditionTextView.setText("بارش ریز همراه با رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;
            case 232:
                climateConditionTextView.setText("بارش ریز شدید همراه با رعد و برق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm));
                break;

            case 300:
                climateConditionTextView.setText("بارش ریز خفیف");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 301:
                climateConditionTextView.setText("بارش ریز");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 302:
                climateConditionTextView.setText("بارش ریز سنگین");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 310:
                climateConditionTextView.setText("بارش سبک");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 311:
                climateConditionTextView.setText("بارش");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 312:
                climateConditionTextView.setText("بارش سنگین");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 313:
                climateConditionTextView.setText("بارش ریز همراه با رگبار");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 314:
                climateConditionTextView.setText("بارش ریز همراه با رگبار سنگین");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;
            case 321:
                climateConditionTextView.setText("بارش ریز رگباری");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_showr_rain));
                break;

            case 500:
                climateConditionTextView.setText("بارش سبک");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 501:
                climateConditionTextView.setText("بارش معتدل");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 502:
                climateConditionTextView.setText("بارش شدید");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 503:
                climateConditionTextView.setText("بارش بسیار شدید");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 504:
                climateConditionTextView.setText("بارش مفرط");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 511:
                climateConditionTextView.setText("بارش منجمد");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 520:
                climateConditionTextView.setText("بارش رگباری سبک");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 521:
                climateConditionTextView.setText("بارش رگباری");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 522:
                climateConditionTextView.setText("بارش رگباری شدید");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;
            case 531:
                climateConditionTextView.setText("بارش  رگباری پراکنده");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_rain));
                break;

            case 600:
                climateConditionTextView.setText("بارش برف سبک");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 601:
                climateConditionTextView.setText("بارش برف");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 602:
                climateConditionTextView.setText("بارش برف سنگین");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 611:
                climateConditionTextView.setText("بارش برف و باران");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 612:
                climateConditionTextView.setText("بارش برف و باران رگباری");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 615:
                climateConditionTextView.setText("بارش برف و باران سبک");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 616:
                climateConditionTextView.setText("بارش برف و باران");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 620:
                climateConditionTextView.setText("بارش برف رگباری سبک");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 621:
                climateConditionTextView.setText("بارش برف رگباری");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;
            case 622:
                climateConditionTextView.setText("بارش برف رگباری سنگین");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_snow));
                break;

            case 701:
                climateConditionTextView.setText("غبارآلود");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 711:
                climateConditionTextView.setText("دود");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 721:
                climateConditionTextView.setText("مه رقیق");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 731:
                climateConditionTextView.setText("گرد باد شن");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 741:
                climateConditionTextView.setText("مه غلیظ");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 751:
                climateConditionTextView.setText("شن");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 761:
                climateConditionTextView.setText("گرد و غبار");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 762:
                climateConditionTextView.setText("خاکستر آتشفشانی");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 771:
                climateConditionTextView.setText("توفان");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;
            case 781:
                climateConditionTextView.setText("گردباد");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_mist));
                break;

            case 800:
                climateConditionTextView.setText("صاف");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_clear_sky));
                break;
            case 801:
                climateConditionTextView.setText("اندکی ابری");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_few_clouds));
                break;
            case 802:
                climateConditionTextView.setText("ابرهای پراکنده");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_scattered_clouds));
                break;
            case 803:
                climateConditionTextView.setText("ابرهای شکسته");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_broken_clouds));
                break;
            case 804:
                climateConditionTextView.setText("پوشیده از ابر");
                climateConditionImageView.setImageDrawable(getDrawable(R.drawable.ic_broken_clouds));
                break;

        }


    }

}
