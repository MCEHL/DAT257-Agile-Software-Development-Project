package com.example.whetherapp;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONObject;
import android.widget.ImageView;
import java.io.IOException;
import java.util.ArrayList;
import android.util.Log;
import static com.example.whetherapp.ForecastQueryParameters.*;


public class MainActivity extends AppCompatActivity {
    /**
     * Main activity of the weather application.
     *
     * <p>Displays the current day's weather forecast and a recommendation
     * based on weather data retrieved from the SMHI API.</p>
     *
     * <p>The activity also updates the weather icon and background
     * according to the forecast and provides navigation to the
     * upcoming seven-day forecast.</p>
     */

    public class MainActivity extends AppCompatActivity {

    /**
     * Initializes the main activity and its user interface.
     *
     * Retrieves weather data from the SMHI API on a background thread,
     * processes the forecast, analyzes today's weather conditions,
     * and determines an appropriate recommendation.
     *
     * Updates the displayed weather information, recommendation,
     * background, and weather icon on the main UI thread.
     *
     * @param savedInstanceState the previously saved activity state,
     *                           or null if no saved state exists
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WEATHERAPP", "MainActivity startade");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button upcomingButton = findViewById(R.id.button);
        upcomingButton.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, UpcomingSevenDays.class);
            startActivity(intent);
        });


        //---API anrop
        new Thread(() -> {
            APICommunication request = new APICommunication();
            String response;

            try {
                response = request.getForecast(
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Log.d("WEATHERAPP", "API-anrop klart");
            Log.d("WEATHERAPP", "API response: " + response);
            JSONObject root;

            try {
                root = new JSONObject(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            ForecastBuilder build = new ForecastBuilder();
            Log.d("WEATHERAPP", "Ska köra createForecasts");

            ArrayList<DayForecast> week;

            try {
                week = build.createForecasts(root);
                Log.d("WEATHERAPP", "createForecasts klar");
                Log.d("WEATHERAPP", "week size = " + week.size());
            } catch (Exception e) {
                Log.e("WEATHERAPP", "FEL I createForecasts", e);
                return;
            }

            //--------

            TextView textView2 = findViewById(R.id.textView3);
            DisplayInput displayInput = new DisplayInput();


            TextView textView = findViewById(R.id.textView2);
            DisplayInput displayInput2 = new DisplayInput();
            //textView.setText(displayInput2.Weather);// ändrar så klassen DisplayInput används.

            WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();//Två rader tillagda så weatherAnalyzer används

            if (week.isEmpty()) { //Spärr om ingen vecka inlagd
                return;
            }
            DayForecast today = week.get(0);
            Log.d("WEATHERAPP", "Average cloudiness = " + today.getAverageCloudiness());
            Log.d("WEATHERAPP", "Will rain = " + today.getRainy());

            double rain = weatherAnalyzer.getRain(today);
            String weatherType = weatherAnalyzer.getWeatherType(today);
            Log.d("WEATHERAPP", "Weather type = " + weatherType);

            RecommendationHandler recommendationHandler = RecommendationHandler.getInstance();
            recommendationHandler.compareToday(new ArrayList<>(recommendationHandler.getRecommendations()), today);

        runOnUiThread(() -> {
            textView2.setText(displayInput.displayRecommendation(RecommendationHandler.getInstance().getCurrentRecommendation().getText()));
            textView.setText(displayInput.displayWeather(rain));

            //---Nytt för att testa bakgrundsändringar beroende på väder
            ConstraintLayout mainLayout = findViewById(R.id.main);
            mainLayout.setBackgroundResource(
                    displayInput.displayBackground(weatherType)
            );

            //---Nytt för att testa ikonanvändning beroende på väderprognos
            ImageView weatherIcon = findViewById(R.id.weatherIcon_Homepage);
            weatherIcon.setImageResource(displayInput.displayWeatherIcon(weatherType));
            });
        }).start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}
