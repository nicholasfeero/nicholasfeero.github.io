package com.zybooks.feero_cs_499capstone;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

// Adapter used to take DataItems objects and apply them to the RecyclerView
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    // List to hold displayed data
    private final List<DataItem> dataList;
    // Listener to handle delete event
    private final OnDeleteClickListener deleteClickListener;
    // Database helper to update data entries
    private final DBHelper dbHelper;

    // Delete definition callback for when delete is clicked
    public interface OnDeleteClickListener {
        void onDeleteClick(long id, int position);
    }

    // Constructor for data list, delete click listener, and DB helper
    public DataAdapter(List<DataItem> dataList, OnDeleteClickListener deleteClickListener,
                       DBHelper dbHelper) {
        this.dataList = dataList;
        this.deleteClickListener = deleteClickListener;
        this.dbHelper = dbHelper;
    }

    // ViewHolder class to manage view references per item
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView weightText, dateText, dailyIntakeText, dailyExerciseText;
        Button deleteButton;

        public DataViewHolder(View itemView) {
            super(itemView);
            // Link UI components from row_item.xml
            weightText = itemView.findViewById(R.id.textWeight);
            dateText = itemView.findViewById(R.id.textDate);
            dailyIntakeText = itemView.findViewById(R.id.textDailyIntake);
            dailyExerciseText = itemView.findViewById(R.id.textDailyExercise);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate row layout from XML per item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new DataViewHolder(view);
    }

    //Helper method to make weight, date, daily intake, and daily exercise cells in grid editable
    private void makeEditable(TextView textView, String title, DataItem item,
                              Consumer<String> fieldSetter) {
        textView.setOnClickListener(v -> {
            EditText input = new EditText(v.getContext());
            input.setText(textView.getText().toString());
            new AlertDialog.Builder(v.getContext())
                    .setTitle(title)
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {
                        String newValue = input.getText().toString();
                        fieldSetter.accept(newValue); // update DataItem field
                        textView.setText(newValue);   // update UI
                        boolean updated = dbHelper.updateDailyWeight(
                                item.getId(),
                                item.getUsername(),
                                item.date,
                                item.weight,
                                item.dailyIntake,
                                item.dailyExercise);
                        if (!updated) {
                            Toast.makeText(v.getContext(), "Failed to save changes.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Entry updated.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        // Get item position
        DataItem item = dataList.get(position);

        // Bind data to text views
        holder.weightText.setText(item.weight);
        holder.dateText.setText(item.date);
        holder.dailyIntakeText.setText(item.dailyIntake);
        holder.dailyExerciseText.setText(item.dailyExercise);

        // Make weight, date, daily intake, and exercise cells editable using helper method
        makeEditable(holder.weightText, "Edit Weight", item, newValue ->
                item.weight = newValue);
        makeEditable(holder.dateText, "Edit Date", item, newValue ->
                item.date = newValue);
        makeEditable(holder.dailyIntakeText, "Edit Daily Intake", item, newValue ->
                item.dailyIntake = newValue);
        makeEditable(holder.dailyExerciseText, "Edit Daily Exercise", item, newValue ->
                item.dailyExercise = newValue);

        // Set delete button behavior and pass row ID and adapter position to the delete listener
        holder.deleteButton.setOnClickListener(v ->
                deleteClickListener.onDeleteClick(item.getId(), position));
    }

    @Override
    public int getItemCount() {
        // Return total number of items in list
        return dataList.size();
    }
}
