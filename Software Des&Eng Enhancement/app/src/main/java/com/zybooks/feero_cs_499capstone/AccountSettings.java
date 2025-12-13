package com.zybooks.feero_cs_499capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class AccountSettings extends AppCompatActivity {

    private EditText editUsername, editPassword;
    private Spinner spinnerTheme;
    private SwitchCompat switchNotifications;
    private DBHelper dbHelper;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Apply current theme to screen
        ThemeHelper.applySavedTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_screen);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Retrieve username passed from other screens
        username = getIntent().getStringExtra("username");

        // Link UI elements
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        spinnerTheme = findViewById(R.id.spinnerTheme);
        switchNotifications = findViewById(R.id.switchNotifications);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button back = findViewById(R.id.buttonBack);

        // Notification switch can be adjusted (if sms permission is granted) by user
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean("notifications", true);
        switchNotifications.setChecked(notificationsEnabled);
        switchNotifications.setEnabled(true);

        // Always enable back buttons
        back.setEnabled(true);

        // Setup theme selector
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[] { "App Default", "Light", "Dark", "System Default" }
        );
        spinnerTheme.setAdapter(adapter);

        // Load saved theme into spinner display, system default if none selected
        String savedTheme = prefs.getString("theme", "System Default");
        int spinnerPosition = adapter.getPosition(savedTheme);
        spinnerTheme.setSelection(spinnerPosition);

        // Handles action of saving settings
        buttonSave.setOnClickListener(v -> saveSettings());

        // Handles navigation from account settings screen to previous screen
        back.setOnClickListener((v -> finish()));
    }

    // Allows real-time (mid-session) updates to notification switch
    @Override
    protected void onResume() {
        super.onResume();
        updateNotificationSwitch();
    }

    // Enables dynamic notification switch that adjusts to Android permission
    private void updateNotificationSwitch() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean savedSetting = prefs.getBoolean("notifications", true);

        boolean smsPermissionGranted =
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED;

        boolean finalState = savedSetting && smsPermissionGranted;

        switchNotifications.setChecked(finalState);
        switchNotifications.setEnabled(smsPermissionGranted);
    }

    // Method that handles saving each element that's altered in the account settings
    private void saveSettings() {
        String newUsername = editUsername.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();
        String selectedTheme = spinnerTheme.getSelectedItem().toString();
        boolean notificationsEnabled = switchNotifications.isChecked();

        // Update username (if not empty)
        if (!newUsername.isEmpty()) {
            if (dbHelper.userExists(newUsername)) {
                Toast.makeText(this, "Username already exists",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            boolean usernameUpdated = dbHelper.updateUsername(username, newUsername);
            if (!usernameUpdated) {
                Toast.makeText(this, "Error updating username. Please try again.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Updates the session's username
            username = newUsername;
        }

        // Update password (if not empty)
        if (!newPassword.isEmpty()) {
            boolean passwordUpdated = dbHelper.updatePassword(username, newPassword);
            if (!passwordUpdated) {
                Toast.makeText(this, "Failed to update password.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Save theme and notification preference
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("theme", selectedTheme);
        editor.putBoolean("notifications", notificationsEnabled);
        editor.apply();

        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();

        // Start LoginScreen after saving settings to reload session preferences
        Intent intent = new Intent(AccountSettings.this, LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
