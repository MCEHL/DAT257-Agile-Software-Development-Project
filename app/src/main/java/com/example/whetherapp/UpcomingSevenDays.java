package com.example.whetherapp;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Screen showing the weather forecast and a recommendation for each of the upcoming seven days.
 * Opened from the "Upcoming seven days" button on the home screen.
 */
public class UpcomingSevenDays extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Låter AppCompatActivity sätta upp fönster och tema.
        setContentView(R.layout.activity_upcoming_seven_days); // Skapar och visar vyerna från XML-filen.
    }
}
