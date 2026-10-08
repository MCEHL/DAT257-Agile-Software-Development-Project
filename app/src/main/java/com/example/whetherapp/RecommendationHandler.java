package com.example.whetherapp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class RecommendationHandler {

    private static RecommendationHandler instance;
    private final List<Recommendation> recommendations = new ArrayList<>();
    private Recommendation currentRecommendation;

    private RecommendationHandler() {
        // Recommendations for today
        recommendations.add(new Recommendation("Do not water your lawn and plants today. The rain does it for you and you save water.",
                0.5));
        recommendations.add(new Recommendation("Water your lawn and plants so they will grow and thrive.", 0.0));
    }

    public static RecommendationHandler getInstance() {
        if (instance == null) instance = new RecommendationHandler();
        return instance;
    }

    public List<Recommendation> getRecommendations() {
        return Collections.unmodifiableList(recommendations);
    }

    /**
     * Compare recommendations with today's forecast
     * @param recommendations A list of recommendations
     * @param dayForecast Today´s forecast
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
     * Compare recommendations with forecasts for the upcoming 7-seven days
     * @param recommendations A list of recommendations
     * @param forecasts The entire week´s forecasts
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

    public Recommendation getCurrentRecommendation() {
        return currentRecommendation;
    }

}

