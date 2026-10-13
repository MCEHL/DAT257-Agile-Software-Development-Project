//package org.example;
package com.example.whetherapp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.time.*;
import java.util.ArrayList;

public class DayForecast {
    //-- Practical data --
    private final LocalDate date;
    private final LocalDateTime forecastCreated;
    private final double stationLongitude, stationLatitude;
    private final JSONArray todaysTimeseries;

    //-- weather data --
    //if there will be rain at any point today, rainy is true, same logic holds for sun and clouds
    private boolean rainy, sunny, cloudy;
    private double minAmountRain, maxAmountRain;
    private double averageCloudiness;

    public DayForecast(LocalDate date, LocalDateTime forecastCreated, double stationLongitude, double stationLatitude, JSONArray todaysTimeseries) {
        this.date = date;
        this.forecastCreated = forecastCreated;
        this.stationLongitude = stationLongitude;
        this.stationLatitude = stationLatitude;
        this.todaysTimeseries = todaysTimeseries;

        updateRain();
        updateClouds();
        updateSun();

    }

    public void updateRain() {
        try {
            for (int i = 0; i < this.todaysTimeseries.length(); i++) {
                // get amount of rain
                JSONObject data = this.todaysTimeseries.getJSONObject(i).getJSONObject("data");

                this.maxAmountRain += data.getDouble("precipitation_amount_max");
                this.minAmountRain += data.getDouble("precipitation_amount_min");
            }

            this.setRainy(this.maxAmountRain > 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateSun(){
        ArrayList<Integer> types = new ArrayList<>();

        try {
            for (int i = 0; i < this.todaysTimeseries.length(); i++) {

                JSONObject data = this.todaysTimeseries.getJSONObject(i).getJSONObject("data");

                types.add(data.getInt("symbol_code"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // check if weather is clear or nearly clear, if yes set sunny to true
        this.setSunny((types.contains(WeatherTypes.valueOf("clear_sky").ordinal())) ||
                types.contains(WeatherTypes.valueOf("nearly_clear_sky").ordinal()));
    }

    public void updateClouds(){
        double totalCloudiness = 0;
        int count = 0;

        ArrayList<Integer> types = new ArrayList<>();

        try {
            for (int i = 0; i < this.todaysTimeseries.length(); i++) {

                JSONObject data = this.todaysTimeseries.getJSONObject(i).getJSONObject("data");

                types.add(data.getInt("symbol_code"));

                if (data.has("cloud_area_fraction")) {
                    totalCloudiness += data.getDouble("cloud_area_fraction");
                    count++;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // check if weather is clear or nearly clear, if yes set sunny to true
        this.setCloudy((types.contains(WeatherTypes.valueOf("variable_cloudiness").ordinal())) ||
                types.contains(WeatherTypes.valueOf("halfclear_sky").ordinal()) ||
                (types.contains(WeatherTypes.valueOf("cloudy_sky").ordinal())) ||
                types.contains(WeatherTypes.valueOf("overcast").ordinal()));

        if (count > 0) {
            averageCloudiness = totalCloudiness / count;
        }
    }

    public LocalDate getDate() {
        return date;
    }
    public double getAverageCloudiness() {
        return averageCloudiness;
    }
    public boolean getRainy() {
        return rainy;
    }
    public void setRainy(boolean rainy) {
        this.rainy = rainy;
    }
    public double getMinAmountRain() {
        return minAmountRain;
    }
    public void setMinAmountRain(double minAmountRain) {
        this.minAmountRain = minAmountRain;
    }
    public double getMaxAmountRain() {
        return maxAmountRain;
    }
    public void setMaxAmountRain(double maxAmountRain) {
        this.maxAmountRain = maxAmountRain;
    }
    public boolean getSunny() {
        return sunny;
    }
    public void setSunny(boolean sunny) {
        this.sunny = sunny;
    }
    public boolean getCloudy() {
        return cloudy;
    }
    public void setCloudy(boolean cloudy) {
        this.cloudy = cloudy;
    }

    public String toString(){

        return "Forecast for: "+this.date+", created on: "+this.forecastCreated+",\n"+
                "by station located at: longitude - "+this.stationLongitude+", latitude - "+this.stationLatitude+"\n"+
                "it will rain today: "+this.rainy +
                "\nminimum amount of rain: "+this.minAmountRain+", maximum amount of rain: "+this.maxAmountRain;
    }
}