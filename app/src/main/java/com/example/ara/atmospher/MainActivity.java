package com.example.ara.atmospher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.ara.atmospher.Interfaces.OpenWeatherService;
import com.example.ara.atmospher.events.ClickManager;
import com.example.ara.atmospher.functions.Background;
import com.example.ara.atmospher.functions.Condition;
import com.example.ara.atmospher.functions.Icon;
import com.example.ara.atmospher.functions.ViewManager;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ara.atmospher.functions.StoreageKt.syncStorage;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "ece8f3c084bf15aef779da23422b4aab";
    private static final String TAG = "TEST_LOG";
    WeatherData weatherData;
    NavigationView navView;
    // TODO: 17/01/2019 use Reverse geocoding to get persian name
    private String CITY_NAME;

    private TextView cityNameTextView;
    private TextView tempTextView;
    private TextView maxTempTextView;
    private TextView minTempTextView;
    private TextView climateConditionTextView;

    private ImageView climateConditionImageView;
    private ImageView backGroundImageView;

    private ImageButton addCityImageButton;
    private ImageButton searchButton;
    private ImageButton closeSearchPlotImageButton;
    private ImageButton drawerHamburgerImageButton;

    private View searchBar;
    private EditText searchInput; // used for city name
    private DrawerLayout mDrawerLayout;

    private Icon icon;
    private Condition condition;
    private Background background;

    private OpenWeatherService service;
    private InputMethodManager imm;

    private ViewManager viewManager;
    private SharedPreferences sharedPref;
    private ClickManager clickManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        initializer();

        setEvents();

        CITY_NAME = syncStorage(sharedPref);

        configureNavigationDrawer();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        service = retrofit.create(OpenWeatherService.class);

        handleRequest(CITY_NAME);

    }

    private Unit onSearch() {
        String searchValue = searchInput.getText().toString();
        if (!searchValue.isEmpty()) {
            viewManager.toggleView(searchBar);
            handleRequest(searchValue);

            //hide soft keyboard
            viewManager.toggleSoftKeyboard(imm);

            searchInput.setText("");
        } else Toast.makeText(this, "متنی وارد نشده!", Toast.LENGTH_SHORT).show();

        return Unit.INSTANCE;

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

    private void handleRequest(final String cityName) {
        Dexter.withActivity(MainActivity.this)//get and set permissions
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new BasePermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        super.onPermissionGranted(response);

                        Call<WeatherData> call = service.weatherCall(cityName, API_KEY);

                        call.enqueue(new Callback<WeatherData>() {
                            @Override
                            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                                weatherData = response.body();
                                if (response.body() != null) {
                                    setView();
                                    CITY_NAME = syncStorage(sharedPref);
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

    private void setEvents() {

        addCityImageButton.setOnClickListener(clickManager);
        searchButton.setOnClickListener(clickManager);
        closeSearchPlotImageButton.setOnClickListener(clickManager);
        drawerHamburgerImageButton.setOnClickListener(clickManager);

        searchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    viewManager.toggleView(searchBar);
                    viewManager.toggleSoftKeyboard(imm);
                    handleRequest(searchInput.getText().toString());
                    searchInput.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    private void initializer() {
        // set views
        cityNameTextView = findViewById(R.id.textView_cityName);
        tempTextView = findViewById(R.id.textView_temperature);
        maxTempTextView = findViewById(R.id.textView_maxTemp);
        minTempTextView = findViewById(R.id.textView_minTemp);
        climateConditionTextView = findViewById(R.id.textView_climateCondition);
        climateConditionImageView = findViewById(R.id.imageView_climateCondition);
        addCityImageButton = findViewById(R.id.imageButton_addCity);
        searchBar = findViewById(R.id.searchPlot);
        searchButton = findViewById(R.id.search_city_button);
        searchInput = findViewById(R.id.editText_search_city);
        closeSearchPlotImageButton = findViewById(R.id.ImageButton_close_search_plot);
        drawerHamburgerImageButton = findViewById(R.id.imageButton_drawer_hamburger);
        backGroundImageView = findViewById(R.id.imageView_background);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.navigation);


        icon = new Icon();
        condition = new Condition();
        background = new Background();

        weatherData = new WeatherData();

        viewManager = new ViewManager();

        sharedPref = this.getSharedPreferences(
                getString(R.string.city_key), Context.MODE_PRIVATE);

        clickManager = new ClickManager(this, this::onSearch);

    }

    @SuppressLint("SetTextI18n")
    public void setView() {
        int id = weatherData.getWeatherList().get(0).getId();

        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        mDrawerLayout.setVisibility(View.VISIBLE);
        cityNameTextView.setText(weatherData.getCityName());
        tempTextView.setText(((int) weatherData.getMainCondition().getTemperature() - 273) + "°");
        maxTempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemp_max() - 273));
        minTempTextView.setText(String.valueOf((int) weatherData.getMainCondition().getTemp_min() - 273));

        climateConditionTextView.setText(condition.getCondition(id));
        climateConditionImageView.setImageDrawable(getDrawable(icon.getIcon(id)));
        backGroundImageView.setImageDrawable(getDrawable(background.getBackground(id)));
    }

}
