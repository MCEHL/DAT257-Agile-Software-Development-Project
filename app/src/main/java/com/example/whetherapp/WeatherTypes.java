package com.example.whetherapp;
/**
 * Represents the different weather conditions provided by the SMHI API.
 *
 * <p>Each enum constant corresponds to a weather symbol code
 * (symbol_code) from the SMHI weather forecast API.
 * The ordinal position of each constant matches its corresponding
 * symbol code, ranging from 1 to 27, with no_type at index 0.</p>
 *
 * <p>Last updated: 2026-10-12.</p>
 */
public enum WeatherTypes {
    no_type,
    clear_sky,
    nearly_clear_sky,
    variable_cloudiness,
    halfclear_sky,
    cloudy_sky,
    overcast,
    fog,
    light_rain_showers,
    moderate_rain_showers,
    heavy_rain_showers,
    thunderstorm,
    light_sleet_showers,
    moderate_sleet_showers,
    heavy_sleet_showers,
    light_snow_showers,
    moderate_snow_showers,
    heavy_snow_showers,
    light_rain,
    moderate_rain,
    heavy_rain,
    thunder,
    light_sleet,
    moderate_sleet,
    heavy_sleet,
    light_snowfall,
    moderate_snowfall,
    heavy_snowfall,
}
