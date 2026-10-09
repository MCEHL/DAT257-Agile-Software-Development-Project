package com.example.whetherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.util.List;

/**
 * Connects a list of days to the RecyclerView on the "Upcoming seven days" screen.
 * The RecyclerView calls the methods below itself; they are never called directly from our code.
 * Each day is shown in a one_day.xml card through a {@link DayViewHolder}.
 */
public class DayAdapter extends RecyclerView.Adapter<DayViewHolder> {
    private final List<LocalDate> days;

    /**
     * @param days the days to show, in the order they should appear
     */
    public DayAdapter(List<LocalDate> days) {
        this.days = days;
    }

    /**
     * Creates a new, empty day card. Only called for the few cards that fit on screen,
     * since the RecyclerView reuses cards when the list is scrolled.
     */
    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // false: RecyclerView lägger själv till kortet i listan när det ska visas.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_day, parent, false);

        return new DayViewHolder(itemView);
    }

    /**
     * Fills an existing card with the day at the given position in the list.
     * Called every time a card is shown, including when it is reused.
     */
    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.bind(days.get(position));
    }

    /**
     * @return the number of days in the list, which decides how many cards are shown
     */
    @Override
    public int getItemCount() {
        return days.size();
    }
}
