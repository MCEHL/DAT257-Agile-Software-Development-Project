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
 * The forecast is retrieved from the SMHI API when the screen opens
 * and each day's weather and recommendation is shown in its card.
 * Tapping a card retrieves the forecast again, e.g. if the first attempt failed.</p>
 *
 * <p>The activity is accessed from the application's home screen
 * and provides navigation back to the previous screen.</p>
 */
public class UpcomingSevenDays extends AppCompatActivity {
    private static final int DAYS_TO_SHOW = 7;

    private DayAdapter adapter;
    private List<LocalDate> days;

    /**
     * Initializes the upcoming seven-day forecast screen.
     *
     * <p>Sets up the layout, back button, and RecyclerView containing
     * the upcoming dates. Configures the adapter to retrieve weather
     * information for all dates right away and again when a date is selected.</p>
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
        days = createUpcomingDays();
        adapter = new DayAdapter(days, date -> fetchForecasts());
        dayList.setAdapter(adapter);
        fetchForecasts();
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
     * Retrieves and displays the weather forecast for all upcoming dates.
     *
     * <p>Fetches forecast data from the SMHI API on a background thread
     * in one request covering the whole week, and searches the returned
     * forecasts for each date. Updates the cards on the main UI thread.</p>
     *
     * <p>If the forecast cannot be retrieved or a date is unavailable,
     * a notification is displayed to the user.</p>
     */
    private void fetchForecasts() {
        for (LocalDate date : days) {
            adapter.setLoading(date);
        }

        new Thread(() -> {
            ArrayList<DayForecast> week = new ArrayList<>();
            try {
                String response = new APICommunication().getForecast(
                        11.9059,
                        57.8558,  // should be a global variable as these numbers now appear multiple times
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
                week = new ForecastBuilder().createForecasts(new JSONObject(response));
            } catch (Exception e) {
                Log.e("WHETHERAPP", "Kunde inte hämta prognos", e);
            }

            ArrayList<DayForecast> result = week;
            runOnUiThread(() -> {
                boolean missing = false;
                for (LocalDate date : days) {
                    DayForecast forecast = null;
                    for (DayForecast day : result) {
                        if (day.getDate().equals(date)) {
                            forecast = day;
                            break;
                        }
                    }
                    adapter.setForecast(date, forecast);
                    missing |= forecast == null;
                }
                if (missing) {
                    Toast.makeText(this, "Some forecasts are unavailable. Tap a day to retry", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
