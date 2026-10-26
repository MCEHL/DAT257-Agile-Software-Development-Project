package com.example.whetherapp;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * Handles HTTP communication with the SMHI weather forecast API.
 * Uses OkHttpClient to perform GET requests and returns raw JSON responses.
 */
public class APICommunication {
    final OkHttpClient client = new OkHttpClient();

    /**
     * Performs a GET request to the given URL and returns the response body as a string.
     *
     * @param url the full URL to request
     * @return the response body as a JSON string
     * @throws IOException if the request fails or the response body is null
     */
    public String getForecast(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     *
     * See <a href="https://opendata.smhi.se/metfcst/snow1gv1/">Meteorological forecasts, SNOW</a> for more information
     * <br/>
     * @param stationLongitude specifies longitude of weather station
     * @param stationLatitude specifies latitude of weather station
     * @param timeseries specifies how many time series you want, -1 gives all available timeseries.
     * @param parameters specifies what data should be included, no parameters gives all available data.
     * @return json object of data
     * @throws IOException if the HTTP request fails
     */
    public String getForecast(double stationLongitude, double stationLatitude, int timeseries, ForecastQueryParameters... parameters) throws IOException {
        String url = "https://opendata-download-metfcst.smhi.se/api/category/snow1g/version/1/geotype/point/lon/"+stationLongitude+"/lat/"+stationLatitude+"/data.json";

        if(timeseries != -1){
            url = url.concat("?timeseries="+timeseries);
        }

        if (parameters.length != 0) {
            String params = StringUtils.join(parameters, ",");
            url = url.concat("?parameters="+params);
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }

}
