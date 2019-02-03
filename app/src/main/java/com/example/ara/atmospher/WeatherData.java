package com.example.ara.atmospher;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherData {

    @SerializedName("coord")
    private Coordination coordination;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private MainCondition mainCondition;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("dt")
    private long informaionDate;

    @SerializedName("sys")
    private DunData sunData;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String CityName;

    @SerializedName(value = "cod", alternate = "code")
    private int operationCod;

    @SerializedName("message")
    private String message;

    public Coordination getCoordination() {
        return coordination;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public String getBase() {
        return base;
    }

    public MainCondition getMainCondition() {
        return mainCondition;
    }

    public String getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getInformaionDate() {
        return informaionDate;
    }

    public DunData getSunData() {
        return sunData;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return CityName;
    }

    public int getOperationCod() {
        return operationCod;
    }

    public String getMessage() {
        return message;
    }

    protected class Coordination {
        @SerializedName("lon")
        double lon;
        @SerializedName("lat")
        double lat;

        public double getLon() {
            return lon;
        }

        public double getLat() {
            return lat;
        }
    }

    protected class Weather {
        @SerializedName("id")
        int id;
        @SerializedName("main")
        String main;
        @SerializedName("description")
        String description;
        @SerializedName("icon")
        String icon;

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    protected class MainCondition {
        @SerializedName("temp")
        double temperature;
        @SerializedName("pressure")
        double pressure;
        @SerializedName("humidity")
        double humidity;
        @SerializedName("temp_min")
        double temp_min;
        @SerializedName("temp_max")
        double temp_max;

        public double getTemperature() {
            return temperature;
        }

        public double getPressure() {
            return pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }
    }

    protected class Wind {
        @SerializedName("speed")
        double speed;
        @SerializedName("deg")
        int degree;

        public double getSpeed() {
            return speed;
        }

        public int getDegree() {
            return degree;
        }

    }

    protected class Clouds {
        @SerializedName("all")
        int all;

        public int getAll() {
            return all;
        }
    }

    protected class DunData {
        @SerializedName("type")
        int type;
        @SerializedName("id")
        int id;
        @SerializedName("message")
        double message;
        @SerializedName("country")
        String country;
        @SerializedName("sunrise")
        long sunrise;
        @SerializedName("sunset")
        long sunset;

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public double getMessage() {
            return message;
        }

        public String getCountry() {
            return country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }

}
