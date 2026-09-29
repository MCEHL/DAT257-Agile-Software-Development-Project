package com.example.whetherapp;
import org.json.JSONObject;

public class DisplayInput {
    public String Weather = "SOL";
    public String recomendation = "Today could be a good day to dry laundry outside!";

    public String displayWeather(JSONObject weatherJson){
        try{
            double rain = weatherJson.getDouble("rain");
            return "Rain: " + rain + " mm/h";
        }
        catch(Exception e){
            return "Weather data unavailable";
        }
    }

    //används inte nu, hårdkodat så !rain=sol :/
    public String getWeatherType(JSONObject weatherJson) {
        try {
            double rain = weatherJson.getDouble("rain");

            if (rain > 0) {
                return "rain";
            } else {
                return "sun";
            }

        } catch (Exception e) {
            return "unknown";
        }
    }

    public int displayWeatherIcon(JSONObject weatherJson) {
        try {
            double rain = weatherJson.getDouble("rain");

            if (rain > 0) {
                return R.drawable.baseline_water_drop_24;
            }
            return 0; // inga fler ikoner ännu

        } catch (Exception e) {
            return 0;
        }
    }
}
