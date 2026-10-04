package com.example.whetherapp;

import android.app.Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Locale;

/**
 * Shows a popup telling the user what to do based on a day's forecast,
 * using the recommendations from RecommendationHandler.
 */
public class RecommendationPopup {

    private final RecommendationHandler recommendationHandler = new RecommendationHandler();
    private final WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();
    private final DisplayInput displayInput = new DisplayInput();

    /**
     * Picks the recommendation with the highest precipitation threshold that the forecast reaches.
     * E.g. 0.0 mm -> "water", 0.5 mm or more -> "do not water".
     */
    public Recommendation getRecommendation(DayForecast forecast) {
        double rain = weatherAnalyzer.getRain(forecast);
        Recommendation best = null;

        for (Recommendation recommendation : recommendationHandler.getRecommendations()) {
            if (rain >= recommendation.getPrecipitationMm()
                    && (best == null || recommendation.getPrecipitationMm() > best.getPrecipitationMm())) {
                best = recommendation;
            }
        }
        return best;
    }

    /** Shows the popup with a weather summary followed by the recommendation. Must be called on the UI thread. */
    public void show(Activity activity, DayForecast forecast) {
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        double rain = weatherAnalyzer.getRain(forecast);
        String weatherType = weatherAnalyzer.getWeatherType(forecast);

        StringBuilder message = new StringBuilder(displayInput.displayWeather(rain));
        if (forecast.getWillRain()) {
            message.append(String.format(Locale.getDefault(), "\nExpected between %.1f and %.1f mm",
                    forecast.getMinAmountRain(), forecast.getMaxAmountRain()));
        }

        Recommendation recommendation = getRecommendation(forecast);
        if (recommendation != null) {
            message.append("\n\n").append(recommendation.getText());
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity)
                .setTitle("Today's recommendation")
                .setMessage(message.toString())
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        int icon = displayInput.displayWeatherIcon(weatherType);
        if (icon != 0) {
            builder.setIcon(icon);
        }

        builder.show();
    }
}
