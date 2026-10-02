package com.example.whetherapp;
import org.json.JSONObject;

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

        if (forecast.getWillRain()){
            return "rain";
        }
        return "sun";
        /*try {
            double rain = weatherJson.getDouble("rain");
            boolean sunny = weatherJson.getBoolean("sunny");

            if (rain > 0) {
                return "rain";
            }
            if (sunny){
                return "sun";
            }

            return "unknown";
        } catch (Exception e) {
            return "unknown";
        }*/
    }
}
