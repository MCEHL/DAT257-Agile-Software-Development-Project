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
}
