package com.zybooks.feero_cs_499capstone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenSelect extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        // Apply current theme to screen
        ThemeHelper.applySavedTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_select);

        // Get logged-in username
        String username = getIntent().getStringExtra("username");

        // Link UI components from screen_select.xml
        Button dailyWeightScreen = findViewById(R.id.buttonWeightScreen);
        Button smsScreen = findViewById(R.id.buttonSMSScreen);
        Button accountSettings = findViewById(R.id.buttonAccountSettings);
        Button back = findViewById(R.id.buttonBack);

        // Always enable dailyWeightScreen, smsScreen, accountSettings, and back buttons
        dailyWeightScreen.setEnabled(true);
        smsScreen.setEnabled(true);
        accountSettings.setEnabled(true);
        back.setEnabled(true);

        // Handles navigation from screen select screen to weight tracking grid screen
        dailyWeightScreen.setOnClickListener(v -> {
            Intent intent = new Intent(ScreenSelect.this, WeightTrackingGrid.class);
            // Pass username to load unique weight tracking grid screen and data
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Handles navigation from screen select screen to SMS notification screen
        smsScreen.setOnClickListener(v -> startActivity(new Intent(
                ScreenSelect.this, SMSNotifications.class)));

        // Handles navigation from screen select screen to account settings screen
        accountSettings.setOnClickListener(v -> {
            Intent intent = new Intent(ScreenSelect.this, AccountSettings.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Handles navigation from screen select screen to previous screen
        back.setOnClickListener(v -> startActivity(new Intent(
                ScreenSelect.this, LoginScreen.class)));
    }
}
