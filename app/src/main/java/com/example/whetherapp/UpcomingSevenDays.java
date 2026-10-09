package com.example.whetherapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen listing the upcoming seven days, starting tomorrow, with space for
 * weather and a recommendation for each day.
 * Opened from the "Upcoming seven days" button on the home screen.
 */
public class UpcomingSevenDays extends AppCompatActivity {
    private static final int DAYS_TO_SHOW = 7;

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
        dayList.setAdapter(new DayAdapter(createUpcomingDays())); // Kopplar dagarna till listan via adaptern.
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
}
