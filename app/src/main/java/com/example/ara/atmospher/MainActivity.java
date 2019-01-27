package com.example.ara.atmospher;

import android.Manifest;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // TODO: 19/01/2019 change top bar to toolbar and configure it
    private static final String API_KEY = "";
    private static final String TAG = "TEST_LOG";
    Weather weather;
    //faeze's chunks
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        getViews();

        setListeners();

        weather = new Weather();

        setWeather();

        configureNavigationDrawer();
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
                Log.i(TAG, "onClick: add city clicked");
                break;
            case R.id.search_city_button:
                Log.i(TAG, "onClick: search city clicked");
                if (!searchCityEditText.getText().toString().equals("")) {
                    Log.i(TAG, "search city text " + searchCityEditText.getText().toString());
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
                        new DownloadWeatherCondition().execute();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        super.onPermissionDenied(response);
                        PermissionListener dialogPermissionListener = DialogOnDeniedPermissionListener.Builder
                                .withContext(MainActivity.this)
                                .withTitle("Internet Permission")
                                .withMessage("We Need Internet to Get the Weather Data")
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
        mDrawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navigation);
    }

    public void parseJSON(String stringResponse) {

        try {
            JSONObject jsonObject = new JSONObject(stringResponse);

            //weather info includes: id, main, description, icon
            String weatherInfo = jsonObject.getString("weather");
            JSONArray array = new JSONArray(weatherInfo);
            for (int i = 0; i < array.length(); i++) {
                //get the id of climate condition
                weather.setWeatherConditionID(array.getJSONObject(i).getInt("id"));
                weather.setWeatherCondition(array.getJSONObject(i).getString("main"));
            }

            weather.setCityName(jsonObject.getString("name"));

            //main info include temp, pressure, humidity, temp_min, temp_max
            String mainInfo = jsonObject.getString("main");
            weather.setTemp(Double.parseDouble(new JSONObject(mainInfo).getString("temp")));
            weather.setMaxTemp(Double.parseDouble(new JSONObject(mainInfo).getString("temp_max")));
            weather.setMinTemp(Double.parseDouble(new JSONObject(mainInfo).getString("temp_min")));

        } catch (Exception e) {
            Toast.makeText(this, "An Error Occurred.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void setView() {

        cityNameTextView.setText(weather.getCityName());
        tempTextView.setText(String.valueOf((int) weather.getTemp() - 273) + "°");
        maxTempTextView.setText(String.valueOf((int) weather.getMaxTemp() - 273));
        minTempTextView.setText(String.valueOf((int) weather.getMinTemp() - 273));

        switch (weather.getWeatherConditionID()/100){
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

        switch (weather.getWeatherConditionID()) {
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

    public class DownloadWeatherCondition extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            try {
                // TODO: 20/01/2019 use volley or retrofit instead
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + CITY_NAME + "&appid=" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;
                    result.append(current);
                    data = reader.read();

                }

                return result.toString();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Can't find this city", Toast.LENGTH_SHORT).show();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            parseJSON(res);
            setView();
        }

    }


}
