package com.example.whetherapp;

import java.util.function.Predicate;

/**
 * Object holding a single recommendation: a text and the condition under which it may be shown.
 */
public class Recommendation {
    private final String text; // The text shown to the user.
    private final Predicate<WeatherDay> condition; // Decides whether the recommendation applies to a given day.

    public Recommendation(String text, Predicate<WeatherDay> condition) {
        this.text = text;
        this.condition = condition;
    }

    public String getText() {
        return text;
    }

    /**
     * Checks whether this recommendation applies to the given day.
     * @param day the weather for the day to check.
     * @return true if the day's weather satisfies the condition, false otherwise.
     */
    public boolean matches(WeatherDay day) {
        return condition.test(day);
    }
}

