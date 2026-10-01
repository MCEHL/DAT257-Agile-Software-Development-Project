package com.example.whetherapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class ForecastBuilder {

    public ArrayList<DayForecast> createForecasts (JSONObject root) throws org.json.JSONException {

        ArrayList<DayForecast> forecasts = new ArrayList<>();

        // ---- Created date, long and lat is same for all days and therefore all forecasts ----

        // Getting the created date
        String createdKey = "createdTime";
        String createdValue = root.getString(createdKey);  //get value
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime createdDate = LocalDateTime.parse(createdValue, formatter);       //format to correct type


        // Getting station longitude and latitude
        JSONObject geometry = root.getJSONObject("geometry");
        JSONArray coordsValue = geometry.getJSONArray("coordinates");

        double longitude = coordsValue.getDouble(0);
        double latitude = coordsValue.getDouble(1);


        // ---- Data for all days are lumped together, first split them into separate days ----

        JSONArray series = root.getJSONArray("timeSeries");
        JSONObject byDay = new JSONObject();   // "2026-09-29" -> JSONArray of entries

        // For every entry in time series:
        for (int i = 0; i < series.length(); i++) {
            JSONObject entry = series.getJSONObject(i); //Get entry

            // Convert to local time
            String day = Instant.parse(entry.getString("intervalParametersStartTime"))
                    .atZone(ZoneId.of("Europe/Stockholm"))
                    .toLocalDate()
                    .toString();   // "2026-09-29"

            if (!byDay.has(day)) { // check if list has this key
                byDay.put(day, new JSONArray()); // if not add key and corresponding array for data storage
            }
            byDay.getJSONArray(day).put(entry); // add data to correct date
        }

    /* Debugging
        for (String day : byDay.keySet()) {
            JSONArray entries = byDay.getJSONArray(day);
            System.out.println(day + ": " + entries);//.toString(4));
        }
    */

        // ---- Now everything is split into separate days, but still in jsonobjects ---
        for (Iterator<String> it = byDay.keys(); it.hasNext(); ) {
            String day = it.next(); //for each day-key in byDay set:
            JSONArray entries = byDay.getJSONArray(day);    //get array of timeseries
            forecasts.add(new DayForecast(LocalDate.parse(day), createdDate, longitude, latitude, entries)); // create new forecastobject and add to array

        }
        return forecasts;
    }


}