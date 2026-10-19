package com.example.whetherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Connects a list of days to the RecyclerView on the "Upcoming seven days" screen.
 * The RecyclerView calls the methods below itself; they are never called directly from our code.
 * Each day is shown in a one_day.xml card through a {@link DayViewHolder}.
 */
public class DayAdapter extends RecyclerView.Adapter<DayViewHolder> {

    /**
     * Listener for tap events on day cards.
     */
    public interface OnDayClickListener {
        /**
         * Called when the user taps a day card.
         *
         * @param date the date of the tapped card
         */
        void onDayClicked(LocalDate date);
    }

    private final List<LocalDate> days;
    private final OnDayClickListener clickListener;
    /** Cached forecasts per date, so that the correct data is shown even when the RecyclerView reuses cards. */
    private final Map<LocalDate, DayForecast> forecasts = new HashMap<>();
    private final Set<LocalDate> loadingDays = new HashSet<>();

    /**
     * @param days the days to show, in the order they should appear
     * @param clickListener told which day was tapped
     */
    public DayAdapter(List<LocalDate> days, OnDayClickListener clickListener) {
        this.days = days;
        this.clickListener = clickListener;
    }

    /**
     * Creates a new, empty day card. Only called for the few cards that fit on screen,
     * since the RecyclerView reuses cards when the list is scrolled.
     */
    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // false: RecyclerView adds the card to the list itself when it is about to be shown.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_day, parent, false);
        return new DayViewHolder(itemView);
    }

    /**
     * Fills an existing card with the day at the given position in the list.
     * Called every time a card is shown, including when it is reused.
     */
    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        LocalDate date = days.get(position);
        holder.bind(date, forecasts.get(date), loadingDays.contains(date));
        holder.itemView.setOnClickListener(v -> clickListener.onDayClicked(date));
    }

    /**
     * @return the number of days in the list, which decides how many cards are shown
     */
    @Override
    public int getItemCount() {
        return days.size();
    }

    /**
     * Marks the given day as loading and refreshes its card.
     * A loading indicator is shown until {@link #setForecast} is called for the same date.
     *
     * @param date the date whose card should show a loading state
     */
    public void setLoading(LocalDate date) {
        loadingDays.add(date);
        notifyItemChanged(days.indexOf(date));
    }

    /**
     * Shows the weather and recommendation for the given day in its card.
     *
     * @param date the date whose card should be updated
     * @param forecast the fetched forecast, or null if none could be fetched
     */
    public void setForecast(LocalDate date, DayForecast forecast) {
        loadingDays.remove(date);
        if (forecast != null) {
            forecasts.put(date, forecast);
        }
        notifyItemChanged(days.indexOf(date));
    }
}
