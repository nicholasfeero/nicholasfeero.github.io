package com.zybooks.feero_cs_360projectthree;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        // Get item position
        DataItem item = dataList.get(position);

        // Bind data to text views
        holder.weightText.setText(item.weight);
        holder.dateText.setText(item.date);
        holder.dailyIntakeText.setText(item.dailyIntake);
        holder.dailyExerciseText.setText(item.dailyExercise);

        // Make weight cell editable by interacting with it
        holder.weightText.setOnClickListener(v -> {
            EditText input = new EditText(v.getContext());
            input.setText(item.weight);
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Weight")
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {
                        item.weight = input.getText().toString();
                        holder.weightText.setText(item.weight);
                        dbHelper.updateDailyWeight(item.getId(), item.getUsername(), item.date,
                                item.weight, item.dailyIntake, item.dailyExercise);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        // Make date cell editable by interacting with it
        holder.dateText.setOnClickListener(v -> {
            EditText input = new EditText(v.getContext());
            input.setText(item.date);
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Date")
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {
                        item.date = input.getText().toString();
                        holder.dateText.setText(item.date);
                        dbHelper.updateDailyWeight(item.getId(), item.getUsername(), item.date,
                                item.weight, item.dailyIntake, item.dailyExercise);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Make daily intake cell editable by interacting with it
        holder.dailyIntakeText.setOnClickListener(v -> {
            EditText input = new EditText(v.getContext());
            input.setText(item.dailyIntake);
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Daily Intake")
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {
                        item.dailyIntake = input.getText().toString();
                        holder.dailyIntakeText.setText(item.dailyIntake);
                        dbHelper.updateDailyWeight(item.getId(), item.getUsername(), item.date,
                                item.weight, item.dailyIntake, item.dailyExercise);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Make daily exercise cell editable by interacting with it
        holder.dailyExerciseText.setOnClickListener(v -> {
            EditText input = new EditText(v.getContext());
            input.setText(item.dailyExercise);
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Daily Exercise")
                    .setView(input)
                    .setPositiveButton("Save", (dialog, which) -> {
                        item.dailyExercise = input.getText().toString();
                        holder.dailyExerciseText.setText(item.dailyExercise);
                        dbHelper.updateDailyWeight(item.getId(), item.getUsername(), item.date,
                                item.weight, item.dailyIntake, item.dailyExercise);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        /*
        Set delete button behavior
        Pass the row ID and adapter position to the delete listener
        */
        holder.deleteButton.setOnClickListener(v ->
                deleteClickListener.onDeleteClick(item.getId(), position));
    }

    @Override
    public int getItemCount() {
        // Return total number of items in list
        return dataList.size();
    }
}