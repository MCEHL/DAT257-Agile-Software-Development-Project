package com.example.whetherapp;
import org.json.JSONObject;

public class WeatherAnalyzer {
    public double getRain(JSONObject weatherJson) {
        try {
            return weatherJson.getDouble("rain");
        } catch (Exception e) {
            return 0.0;
        }
    }
    public String getWeatherType(JSONObject weatherJson) {
        try {
            double rain = weatherJson.getDouble("rain");
            if (rain > 0) {
                return "rain";
            }
            return "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }
}
