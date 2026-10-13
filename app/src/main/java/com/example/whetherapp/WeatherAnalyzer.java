package com.example.whetherapp;

public class WeatherAnalyzer {
    public double getRain(DayForecast forecast) {
       return forecast.getMaxAmountRain();
        /*try {
            return weatherJson.getDouble("rain");
        } catch (Exception e) {
            return 0.0;
        }*/
    }
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
