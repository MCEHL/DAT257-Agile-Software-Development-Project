package com.example.whetherapp;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    /**
     * @param itemView an inflated one_day.xml layout
     */
    public DayViewHolder(View itemView) {
        super(itemView);
        this.dayName = itemView.findViewById(R.id.dayName);
        this.dayDate = itemView.findViewById(R.id.dayDate);
    }

    /**
     * Shows the name and date of the given day in the card.
     *
     * @param date the day to display
     */
    public void bind(LocalDate date) {
        dayName.setText(date.format(DAY_NAME_FORMAT));
        dayDate.setText(date.format(DAY_DATE_FORMAT));
    }
}
