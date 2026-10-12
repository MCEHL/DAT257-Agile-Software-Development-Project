package com.example.whetherapp;
//import org.json.JSONObject;

import java.util.Locale;


public class DisplayInput {
    // public String Weather = "SOL";
    // public String recomendation = "Today could be a good day to dry laundry outside!";

    public String displayWeather(double rain) {
        if (rain >= 0.05) {
            return String.format(Locale.US, "Rain: %.1f mm", rain);
        } else {
            return "No rain expected";
        }
    }

    public int displayWeatherIcon(String weatherType) {

        if (weatherType.equals("rain")) {
            return R.drawable.baseline_water_drop_24;
        }

        if (weatherType.equals("sun")) {
            return R.drawable.baseline_wb_sunny_24;
        }
        if (weatherType.equals("cloudy")) {
            return R.drawable.baseline_cloud_24;
        }
        return 0;
    }

    public int displayBackground(String weatherType) {
        if (weatherType.equals("rain")) {
            return R.drawable.rainbackground;
        }
        if (weatherType.equals("sun")) {
            return R.drawable.sunbackground;
        }
        if (weatherType.equals("cloudy")) {
            return R.drawable.background_cloudy;
        }
        return 0;
    }

    public String displayRecommendation(String recommendation) {
        return recommendation;
    }
}

