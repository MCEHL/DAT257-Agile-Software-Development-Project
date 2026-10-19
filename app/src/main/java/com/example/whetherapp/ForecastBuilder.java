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

/**
 * Parses a raw SMHI forecast JSON response into a list of {@link DayForecast} objects,
 * one per calendar day covered by the response.
 */
public class ForecastBuilder {

    /**
     * Parses the root JSON object from the SMHI API and creates one {@link DayForecast}
     * per calendar day found in the timeseries.
     *
     * @param root the root JSON object returned by the SMHI forecast API
     * @return a list of daily forecasts in the order they appear in the response
     * @throws org.json.JSONException if a required field is missing or has an unexpected type
     */
    public ArrayList<DayForecast> createForecasts(JSONObject root) throws org.json.JSONException {

        ArrayList<DayForecast> forecasts = new ArrayList<>();

        // ---- createdTime and coordinates are the same for all days ----

        String createdValue = root.getString("createdTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime createdDate = LocalDateTime.parse(createdValue, formatter);

        JSONArray coordsValue = root.getJSONObject("geometry").getJSONArray("coordinates");
        double longitude = coordsValue.getDouble(0);
        double latitude = coordsValue.getDouble(1);

        // ---- All timeseries entries are lumped together; split them into separate days ----

        JSONArray series = root.getJSONArray("timeSeries");
        JSONObject byDay = new JSONObject(); // "2026-09-29" -> JSONArray of entries

        for (int i = 0; i < series.length(); i++) {
            JSONObject entry = series.getJSONObject(i);

            // Convert UTC intervalParametersStartTime to local Stockholm date
            String day = Instant.parse(entry.getString("intervalParametersStartTime"))
                    .atZone(ZoneId.of("Europe/Stockholm"))
                    .toLocalDate()
                    .toString(); // "2026-09-29"

            if (!byDay.has(day)) {
                byDay.put(day, new JSONArray());
            }
            byDay.getJSONArray(day).put(entry);
        }

        // ---- Create one DayForecast per day ----

        for (Iterator<String> it = byDay.keys(); it.hasNext(); ) {
            String day = it.next();
            JSONArray entries = byDay.getJSONArray(day);
            forecasts.add(new DayForecast(LocalDate.parse(day), createdDate, longitude, latitude, entries));
        }

        return forecasts;
    }
}
