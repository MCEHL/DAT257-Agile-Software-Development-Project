package com.example.whetherapp;

import java.util.Locale;

/**
 * Translates weather data into displayable text, icons, and backgrounds.
 */
public class DisplayInput {

    /**
     * Formats the rain amount as a human-readable string.
     *
     * @param rain precipitation amount in mm
     * @return a formatted rain string, or "No rain expected" if below the threshold
     */
    public String displayWeather(double rain) {
        if (rain >= 0.05) {
            return String.format(Locale.US, "Rain: %.1f mm", rain);
        } else {
            return "No rain expected";
        }
    }

    /**
     * Returns the drawable resource id for the weather icon matching the given type.
     *
     * @param weatherType "sun", "rain", or "cloudy", as returned by {@link WeatherAnalyzer#getWeatherType}
     * @return a drawable resource id, or 0 if the weather type is unknown
     */
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

    /**
     * Returns the drawable resource id for the background matching the given weather type.
     *
     * @param weatherType "sun", "rain", or "cloudy", as returned by {@link WeatherAnalyzer#getWeatherType}
     * @return a drawable resource id, or 0 if the weather type is unknown
     */
    public int displayBackground(String weatherType) {
        if (weatherType.equals("rain")) {
            return R.drawable.rainbackground;
        }
        if (weatherType.equals("sun")) {
            return R.drawable.sunbackground;
        }
        if (weatherType.equals("cloudy")) {
            return R.drawable.cloudbackground;
        }
        return 0;
    }

    /**
     * Returns the recommendation text as-is for display.
     *
     * @param recommendation the recommendation text to display
     * @return the same string unchanged
     */
    public String displayRecommendation(String recommendation) {
        return recommendation;
    }

    /**
     * Chooses the background color for a day card on the upcoming seven days page.
     *
     * @param weatherType "sun", "rain", or "cloudy", as returned by {@link WeatherAnalyzer#getWeatherType}
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
