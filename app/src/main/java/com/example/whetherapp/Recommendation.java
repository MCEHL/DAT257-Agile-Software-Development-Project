package com.example.whetherapp;

/**
 * Object holding a single recommendation: a text and the condition under which it may be shown.
 */
public class Recommendation {
    private final String text; // The text shown to the user.
    private final double precipitationMm; // The precipitation.

    public Recommendation(String text, double precipitationMm) {
        this.text = text;
        this.precipitationMm = precipitationMm;
        //this.condition = condition;
    }

    public String getText() {
        return text;
    }

    public double getPrecipitationMm() {
        return precipitationMm;}
}

