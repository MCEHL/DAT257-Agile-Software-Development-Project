package com.example.whetherapp;

import android.os.Bundle;

import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONObject;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ---------------------------------
        // Test JSON
        // ---------------------------------
        JSONObject weatherJson = new JSONObject();

        try {
            weatherJson.put("rain", 1.0);

        } catch (Exception e) {

        }
        //------------------------------------

        TextView textView2 = findViewById(R.id.textView3);
        DisplayInput displayInput = new DisplayInput();
        textView2.setText(displayInput.recomendation);// ändrar så klassen DisplayInput används.

        TextView textView = findViewById(R.id.textView2);
        DisplayInput displayInput2 = new DisplayInput();
       //textView.setText(displayInput2.Weather);// ändrar så klassen DisplayInput används.
        textView.setText(displayInput2.displayWeather(weatherJson));

        //---Nytt för att testa ikonanvändning beroende på väderprognos

        ImageView weatherIcon = findViewById(R.id.rainIcon);
        weatherIcon.setImageResource(displayInput.displayWeatherIcon(weatherJson));
        //

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
