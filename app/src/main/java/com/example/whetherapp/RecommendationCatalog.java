package com.example.whetherapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds all recommendations available in the app, each with its condition.
 */
public class RecommendationCatalog {

    private static final double RAIN_LIMIT_MM = 0.5; // Precipitation at or above this counts as a rainy day.
    private final List<Recommendation> recommendations;

    public RecommendationCatalog() {
        this.recommendations = new ArrayList<>();

        // Rain
        recommendations.add(new Recommendation(
                            "Do not water your lawn and plants today. The rain does it for you and you save water.",
                            day -> day.getPrecipitationMm() >= RAIN_LIMIT_MM));

        // No rain
        recommendations.add(new Recommendation(
                            "Water your lawn and plants so they will grow and thrive.",
                            day -> day.getPrecipitationMm() < RAIN_LIMIT_MM));
    }

    /**
     * Returns all recommendations as a read-only list.
     */
    public List<Recommendation> getRecommendations() {
        return Collections.unmodifiableList(recommendations);
    }

}
