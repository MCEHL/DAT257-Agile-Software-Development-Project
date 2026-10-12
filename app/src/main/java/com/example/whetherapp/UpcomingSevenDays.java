package com.example.whetherapp;

import android.os.Bundle;
import android.util.Log;
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
 * Screen listing the upcoming seven days, starting tomorrow.
 * Tapping a day fetches its forecast and shows the weather and a recommendation in the card.
 * Opened from the "Upcoming seven days" button on the home screen.
 */
public class UpcomingSevenDays extends AppCompatActivity {
    private static final int DAYS_TO_SHOW = 7;

    private DayAdapter adapter;

    /**
     * Called by Android when the screen is opened. Shows the layout and fills the list with one card per day.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Låter AppCompatActivity sätta upp fönster och tema.
        setContentView(R.layout.activity_upcoming_seven_days); // Skapar och visar vyerna från XML-filen.
        RecyclerView dayList = findViewById(R.id.dayList);
        // Lägger korten under varandra. Utan LayoutManager visas ingenting.
        dayList.setLayoutManager(new LinearLayoutManager(this));
        dayList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new DayAdapter(createUpcomingDays(), this::fetchForecastFor);
        dayList.setAdapter(adapter); // Kopplar dagarna till listan via adaptern.
    }

    /**
     * Creates the days to show, starting tomorrow since today is shown on the home screen.
     *
     * @return the upcoming {@value #DAYS_TO_SHOW} days in order
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
     * Fetches the forecast from the API on a background thread and shows the given day's forecast in its card.
     *
     * @param date the day that was tapped
     */
    private void fetchForecastFor(LocalDate date) {
        adapter.setLoading(date);

        // Nätverksanrop får inte göras på UI-tråden.
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
