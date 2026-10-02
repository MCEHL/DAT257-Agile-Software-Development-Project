package com.example.whetherapp;

public enum ForecastQueryParameters {
    /**
     * For more information about parameters please go to: <a href="https://opendata.smhi.se/metfcst/snow1gv1/parameters">Meteorological forecasts, SNOW - parameters</a>
     * <br/>
     * The following parameters aren't calculated or have special return values:
     * precipitation_amount_max, precipitation_amount_min, precipitation_frozen_part
     * Please consult the linked site for more information
     */

    air_pressure_at_mean_sea_level,
    air_temperature,
    cloud_area_fraction,
    cloud_base_altitude,
    cloud_top_altitude,
    high_type_cloud_area_fraction,
    low_type_cloud_area_fraction,
    medium_type_cloud_area_fraction,
    precipitation_amount_max,
    precipitation_amount_mean,
    precipitation_amount_mean_deterministic,
    precipitation_amount_median,
    precipitation_amount_min,
    precipitation_frozen_part,
    predominant_precipitation_type_at_surface,
    probability_of_frozen_precipitation,
    probability_of_precipitation,
    relative_humidity,
    symbol_code,
    thunderstorm_probability,
    visibility_in_air,
    wind_from_direction,
    wind_speed,
    wind_speed_of_gust



}