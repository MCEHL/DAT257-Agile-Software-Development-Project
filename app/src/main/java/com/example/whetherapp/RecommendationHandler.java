package com.example.whetherapp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class RecommendationHandler {

    private static RecommendationHandler instance;
    private final List<Recommendation> recommendations = new ArrayList<>();
    private Recommendation  currentRecommendation;

    private RecommendationHandler() {

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
     * Compare weather with recommendations
     * @param recommendations
     * @param forecasts
     * @return One recommendation if the recommendation matches with the forecast
     */
    public String compare(ArrayList<Recommendation> recommendations, List<DayForecast> forecasts) {
        for (Recommendation recommendation : recommendations) {
            for (DayForecast forecast : forecasts) {
                if (forecast.getMaxAmountRain() >= recommendation.getPrecipitationMm()
                        && (currentRecommendation == null
                        || recommendation.getPrecipitationMm() > currentRecommendation.getPrecipitationMm())) {
                    currentRecommendation = recommendation;
                }
            }
        }
        return "No matches found";
    }

    public Recommendation getCurrentRecommendation() {
        return currentRecommendation;
    }

}

