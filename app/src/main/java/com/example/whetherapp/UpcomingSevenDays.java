package com.example.whetherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.whetherapp.ForecastQueryParameters.*;

/**
 * Activity that displays weather forecasts for the upcoming seven days.
 *
 * <p>Displays a list of dates starting from tomorrow.
 * When a user selects a date, the corresponding forecast is retrieved
 * from the SMHI API and displayed in the selected day's card.</p>
 *
 * <p>The activity is accessed from the application's home screen
 * and provides navigation back to the previous screen.</p>
 */
public class UpcomingSevenDays extends AppCompatActivity {
    private static final int DAYS_TO_SHOW = 7;

    private DayAdapter adapter;

    /**
     * Initializes the upcoming seven-day forecast screen.
     *
     * <p>Sets up the layout, back button, and RecyclerView containing
     * the upcoming dates. Configures the adapter to retrieve weather
     * information when a date is selected.</p>
     *
     * @param savedInstanceState the previously saved activity state,
     *                           or null if no saved state exists
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_seven_days);
        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v ->{finish();});
        RecyclerView dayList = findViewById(R.id.dayList);

        dayList.setLayoutManager(new LinearLayoutManager(this));
        dayList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new DayAdapter(createUpcomingDays(), this::fetchForecastFor);
        dayList.setAdapter(adapter);
    }

    /**
     * Creates a list of dates for the upcoming seven days.
     *
     * <p>Starts from tomorrow because today's forecast is displayed
     * on the application's home screen.</p>
     *
     * @return a list containing {@value #DAYS_TO_SHOW} consecutive
     *         dates, starting from tomorrow
     */
    private List<LocalDate> createUpcomingDays() {
        List<LocalDate> days = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= DAYS_TO_SHOW; i++) {
            days.add(today.plusDays(i));
        }
        return days;
    }
    /**
     * Retrieves and displays the weather forecast for a selected date.
     *
     * <p>Fetches forecast data from the SMHI API on a background thread
     * and searches the returned forecasts for the requested date.
     * Updates the corresponding card on the main UI thread.</p>
     *
     * <p>If the forecast cannot be retrieved or the requested date
     * is unavailable, a notification is displayed to the user.</p>
     *
     * @param date the date for which the forecast should be retrieved
     */
    private void fetchForecastFor(LocalDate date) {
        adapter.setLoading(date);

        new Thread(() -> {
            DayForecast forecast = null;
            try {
                String response = new APICommunication().getForecast(
                        11.9059,
                        57.8558,
                        -1,
                        symbol_code,
                        cloud_area_fraction,
                        probability_of_precipitation,
                        precipitation_amount_max,
                        precipitation_amount_mean,
                        precipitation_amount_min,
                        predominant_precipitation_type_at_surface,
                        relative_humidity,
                        wind_speed
                );
                ArrayList<DayForecast> week = new ForecastBuilder().createForecasts(new JSONObject(response));
                for (DayForecast day : week) {
                    if (day.getDate().equals(date)) {
                        forecast = day;
                        break;
                    }
                }
            } catch (Exception e) {
                Log.e("WHETHERAPP", "Kunde inte hämta prognos för " + date, e);
            }

            DayForecast result = forecast;
            runOnUiThread(() -> {
                adapter.setForecast(date, result);
                if (result == null) {
                    Toast.makeText(this, "No forecast available for " + date, Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
