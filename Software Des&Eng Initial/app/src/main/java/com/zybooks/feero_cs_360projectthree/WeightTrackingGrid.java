package com.zybooks.feero_cs_360projectthree;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WeightTrackingGrid extends AppCompatActivity {

    // List to store daily weight entries
    private final List<DataItem> dataList = new ArrayList<>();
    // Adapter for RecyclerView to connect and display data
    private DataAdapter adapter;
    // Database helper
    private DBHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establish and load weight tracking grid layout
        setContentView(R.layout.grid_screen);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Get logged-in username
        username = getIntent().getStringExtra("username");

        // Initialize RecyclerView for displaying daily weight entries
        RecyclerView recyclerView = findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load existing data from user database into the list on start
        loadDataFromDatabase();

        // Creates adapter with delete functionality by row ID for each entry
        adapter = new DataAdapter(dataList, (id, position) -> {
            boolean deleted = dbHelper.deleteDailyWeight((int) id, username);
            if (deleted) {
                dataList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, dataList.size() - position);
                Toast.makeText(this, "Entry deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error deleting entry", Toast.LENGTH_SHORT).show();
            }
        }, dbHelper); // pass DBHelper

        // Attach adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        // Link UI components from grid_screen.xml
        EditText inputWeight = findViewById(R.id.inputWeight);
        EditText inputDate = findViewById(R.id.inputDate);
        EditText inputDailyIntake = findViewById(R.id.inputDailyIntake);
        EditText inputDailyExercise = findViewById(R.id.inputExercise);
        Button addButton = findViewById(R.id.buttonAdd);
        Button back = findViewById(R.id.buttonBack);
        Button sms = findViewById(R.id.buttonSMS);

        // Always enable back and SMS buttons
        back.setEnabled(true);
        sms.setEnabled(true);

        // Handles navigation from weight tracking grid screen to login screen
        back.setOnClickListener(v -> startActivity(new Intent(
                WeightTrackingGrid.this, LoginScreen.class)));

        // Handles navigation from weight tracking grid screen to SMS notification screen
        sms.setOnClickListener(v -> startActivity(new Intent(
                WeightTrackingGrid.this, SMSNotifications.class)));

        // Handles adding a new entry to the database
        addButton.setOnClickListener(v -> {
            // Store input of each data field
            String weight = inputWeight.getText().toString();
            String date = inputDate.getText().toString();
            String dailyIntake = inputDailyIntake.getText().toString();
            String dailyExercise = inputDailyExercise.getText().toString();

            // Check if there are any empty data fields
            if (!weight.isEmpty() && !date.isEmpty() &&
                    !dailyIntake.isEmpty() && !dailyExercise.isEmpty()) {

                // If all data fields are filled, insert new entry into daily weight database
                long id = dbHelper.insertDailyWeight(username, weight, date, dailyIntake,
                        dailyExercise);
                if (id != -1) {
                    DataItem newItem = new DataItem(id, username, weight, date, dailyIntake,
                            dailyExercise);
                    dataList.add(newItem);
                    adapter.notifyItemInserted(dataList.size() - 1);

                    // After adding entry, clear input fields
                    inputWeight.setText("");
                    inputDate.setText("");
                    inputDailyIntake.setText("");
                    inputDailyExercise.setText("");

                    Toast.makeText(this, "Entry added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error adding entry",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Loads all weight entries from database into list
    private void loadDataFromDatabase() {
        dataList.clear();
        dataList.addAll(dbHelper.getAllWeightEntries(username));
    }
}
