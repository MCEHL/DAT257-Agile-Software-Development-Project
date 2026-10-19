package com.example.whetherapp;

/**
 * Represents a weather-based recommendation.
 *
 * <p>Stores the recommendation text displayed to the user
 * and a precipitation value associated with the recommendation.</p>
 */
public class Recommendation {
    private final String text; // The text shown to the user.
    private final double precipitationMm; // The precipitation.

    /**
     * Creates a recommendation with the specified text and precipitation value.
     *
     * @param text the recommendation text to display to the user
     * @param precipitationMm the precipitation value in millimeters
     *                        associated with the recommendation
     */
    public Recommendation(String text, double precipitationMm) {
        this.text = text;
        this.precipitationMm = precipitationMm;
    }

    /**
     * Returns the recommendation text.
     *
     * @return the text displayed to the user
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the precipitation value associated with the recommendation.
     *
     * @return the precipitation value in millimeters
     */
    public double getPrecipitationMm() {
        return precipitationMm;}
}

