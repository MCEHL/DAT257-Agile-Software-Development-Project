package com.example.whetherapp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages weather-based recommendations for the application.
 *
 * <p>Stores available recommendations and selects a suitable recommendation
 * by comparing precipitation values with weather forecasts.
 * Uses the Singleton pattern to provide a shared instance.</p>
 */
public class RecommendationHandler {

    private static RecommendationHandler instance;
    private final List<Recommendation> recommendations = new ArrayList<>();
    private Recommendation currentRecommendation;
    /**
     * Initializes the recommendation handler with predefined
     * recommendations based on precipitation.
     *
     * <p>The constructor is private to prevent other classes
     * from creating additional instances.</p>
     */
    private RecommendationHandler() {

        recommendations.add(new Recommendation("Do not water your lawn and plants today. The rain does it for you and you save water.",
                0.5));
        recommendations.add(new Recommendation("Water your lawn and plants so they will grow and thrive.", 0.0));
    }
    /**
     * Returns the shared instance of the recommendation handler.
     *
     * <p>Creates the instance if it does not already exist.</p>
     *
     * @return the shared RecommendationHandler instance
     */
    public static RecommendationHandler getInstance() {
        if (instance == null) instance = new RecommendationHandler();
        return instance;
    }
    /**
     * Returns the available weather-based recommendations.
     *
     * @return an unmodifiable list of recommendations
     */
    public List<Recommendation> getRecommendations() {
        return Collections.unmodifiableList(recommendations);
    }

    /**
     * Selects a recommendation based on today's weather forecast.
     *
     * <p>Compares the forecast's maximum precipitation with the
     * precipitation values of the provided recommendations.
     * Selects the matching recommendation with the highest
     * precipitation value.</p>
     *
     * @param recommendations the recommendations to compare
     * @param dayForecast the weather forecast for today
     */
    public void compareToday(ArrayList<Recommendation> recommendations, DayForecast dayForecast) {
        currentRecommendation = null;
        for (Recommendation recommendation : recommendations) {{
                if (dayForecast.getMaxAmountRain() >= recommendation.getPrecipitationMm()
                        && (currentRecommendation == null
                        || recommendation.getPrecipitationMm() > currentRecommendation.getPrecipitationMm())) {
                            currentRecommendation = recommendation;
                            }
                }
        }
    }

    /**
     * Selects a recommendation based on forecasts for the upcoming seven days.
     *
     * <p>Compares each forecast's maximum precipitation with the
     * precipitation values of the provided recommendations.
     * Selects the matching recommendation with the highest
     * precipitation value across the supplied forecasts.</p>
     *
     * @param recommendations the recommendations to compare
     * @param forecasts the daily weather forecasts for the upcoming days
     */
    public void compare7days(ArrayList<Recommendation> recommendations, List<DayForecast> forecasts) {
        currentRecommendation = null;
        for (Recommendation recommendation : recommendations) {
            for (DayForecast forecast : forecasts) {
                if (forecast.getMaxAmountRain() >= recommendation.getPrecipitationMm()
                        && (currentRecommendation == null
                        || recommendation.getPrecipitationMm() > currentRecommendation.getPrecipitationMm())) {
                    currentRecommendation = recommendation;
                }
            }
        }
    }
    /**
     * Returns the recommendation selected by the latest comparison.
     *
     * @return the selected recommendation, or null if no matching
     *         recommendation has been found
     */
    public Recommendation getCurrentRecommendation() {
        return currentRecommendation;
    }

}

