package com.example.whetherapp;
//import org.json.JSONObject;

public class DisplayInput {
    // public String Weather = "SOL";
    // public String recomendation = "Today could be a good day to dry laundry outside!";

    public String displayWeather(double rain) {
        if (rain > 0) {
            return "Rain: " + rain + " mm";
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

        return 0;
    }

    public String displayRecommendation(String recommendation) {
        return recommendation;
    }
}

