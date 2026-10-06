package com.example.whetherapp;

import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WEATHERAPP", "MainActivity startade");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


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

            double rain = weatherAnalyzer.getRain(today);
            String weatherType = weatherAnalyzer.getWeatherType(today);

            RecommendationHandler recommendationHandler = RecommendationHandler.getInstance();
            recommendationHandler.compareToday(new ArrayList<>(recommendationHandler.getRecommendations()), today);

        runOnUiThread(() -> {
            textView2.setText(displayInput.displayRecommendation(RecommendationHandler.getInstance().getCurrentRecommendation().getText()));
            textView.setText(displayInput.displayWeather(rain));

            //---Nytt för att testa ikonanvändning beroende på väderprognos
            ImageView weatherIcon = findViewById(R.id.rainIcon);
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
