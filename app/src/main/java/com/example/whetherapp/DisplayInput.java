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

        return 0;
    }

    public String displayRecommendation(String recommendation) {
        return recommendation;
    }

    /**
     * Chooses the background color for a day card on the upcoming seven days page.
     * "cloudy" is only returned by WeatherAnalyzer once cloudiness is supported.
     *
     * @param weatherType "sun", "rain" or "cloudy", as returned by WeatherAnalyzer.getWeatherType
     * @return a color resource id, or 0 for no background if the weather type is unknown
     */
    public int displayDayBackgroundColor(String weatherType) {
        switch (weatherType) {
            case "rain":
                return R.color.weather_rain;
            case "sun":
                return R.color.weather_sun;
            case "cloudy":
                return R.color.weather_cloudy;
            default:
                return 0;
        }
    }
}