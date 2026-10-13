package com.example.whetherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Holds the views of one day card (one_day.xml) in the upcoming seven days list.
 * Looks up the views once when the card is created, so the card can be reused
 * for different days when the list is scrolled.
 */
public class DayViewHolder extends RecyclerView.ViewHolder {
    private static final DateTimeFormatter DAY_NAME_FORMAT = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
    private static final DateTimeFormatter DAY_DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);

    private final TextView dayName;
    private final TextView dayDate;
    private final TextView weatherText;
    private final ImageView weatherIcon;
    private final TextView recommendationText;

    /**
     * @param itemView an inflated one_day.xml layout
     */
    public DayViewHolder(View itemView) {
        super(itemView);
        this.dayName = itemView.findViewById(R.id.dayName);
        this.dayDate = itemView.findViewById(R.id.dayDate);
        this.weatherText = itemView.findViewById(R.id.weatherText);
        this.weatherIcon = itemView.findViewById(R.id.weatherIcon);
        this.recommendationText = itemView.findViewById(R.id.recommendationText);
    }

    /**
     * Shows the given day in the container
     *
     * @param date the day to display
     * @param forecast the day's forecast, or null if it has not been fetched yet
     * @param loading true while the forecast is being fetched
     */
    public void bind(LocalDate date, DayForecast forecast, boolean loading) {
        dayName.setText(date.format(DAY_NAME_FORMAT));
        dayDate.setText(date.format(DAY_DATE_FORMAT));

        if (forecast == null) {
            weatherText.setText("");
            weatherIcon.setImageDrawable(null);
            recommendationText.setText(loading ? "Loading..." : "Tap to see recommendation");
            itemView.setBackgroundResource(0); // Kortet kan vara återanvänt från en dag med färg.
            return;
        }

        WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();
        DisplayInput displayInput = new DisplayInput();
        RecommendationHandler recommendationHandler = RecommendationHandler.getInstance();
        recommendationHandler.compareToday(new ArrayList<>(recommendationHandler.getRecommendations()), forecast);

        String weatherType = weatherAnalyzer.getWeatherType(forecast);

        weatherText.setText(displayInput.displayWeather(weatherAnalyzer.getRain(forecast)));
        weatherIcon.setImageResource(displayInput.displayWeatherIcon(weatherType));
        recommendationText.setText(displayInput.displayRecommendation(recommendationHandler.getCurrentRecommendation().getText()));

        itemView.setBackgroundResource(displayInput.displayDayBackgroundColor(weatherType));
    }
}
