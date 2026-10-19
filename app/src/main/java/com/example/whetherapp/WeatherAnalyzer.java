package com.example.whetherapp;


/**
 * Analyzes weather forecast data to determine precipitation
 * and general weather conditions.
 *
 * <p>Uses information from a DayForecast object to retrieve
 * rainfall amounts and classify the weather as rainy,
 * cloudy, or sunny.</p>
 */
public class WeatherAnalyzer {

    /**
     * Retrieves the maximum expected precipitation for a given day.
     *
     * @param forecast the daily weather forecast to analyze
     * @return the maximum expected precipitation in millimeters
     */
    public double getRain(DayForecast forecast) {
       return forecast.getMaxAmountRain();
        /*try {
            return weatherJson.getDouble("rain");
        } catch (Exception e) {
            return 0.0;
        }*/
    }

    /**
     * Determines the general weather type based on the daily forecast.
     *
     * <p>Returns "rain" if precipitation is expected,
     * "cloudy" if the forecast indicates cloudy conditions,
     * or "sun" otherwise. Rain takes priority over cloudiness.</p>
     *
     * @param forecast the daily weather forecast to analyze
     * @return the weather type as "rain", "cloudy", or "sun"
     */
    public String getWeatherType(DayForecast forecast) {

        if (forecast.getRainy()){
            return "rain";
        }
        if (forecast.getCloudy()) {
            return "cloudy";
        }
        return "sun";
    }
}
