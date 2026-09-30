package com.example.whetherapp;

/**
 * Immutable value object holding the weather data for a single day.
 * Used as input when deciding which recommendations apply.
 */
public class WeatherDay {
    private final double precipitationMm; // Total precipitation during the day, in mm.
    private final double minTemperatureC; // Lowest temperature in the day, in °C.
    private final double maxTemperatureC; // Highest temperature in the day, in °C.
    private final double windSpeedMs;     // Wind speed, in m/s.
    public WeatherDay(double precipitationMm, double minTemperatureC, double maxTemperatureC, double windSpeedMs){
        this.precipitationMm = precipitationMm;
        this.minTemperatureC = minTemperatureC;
        this.maxTemperatureC = maxTemperatureC;
        this.windSpeedMs = windSpeedMs;
    }
    public double getPrecipitationMm() {
        return precipitationMm;
    }
    public double getMinTemperatureC() {
        return minTemperatureC;
    }
    public double getMaxTemperatureC() {
        return maxTemperatureC;
    }
    public double getWindSpeedMs() {
        return windSpeedMs;
    }
}
