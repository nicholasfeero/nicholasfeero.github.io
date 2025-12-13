package com.zybooks.feero_cs_360projectthree;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SMSNotifications extends AppCompatActivity {
    // Request code for SMS permission
    private static final int SMS_PERMISSION_CODE = 100;
    private EditText inputPhone;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_screen);

        // Get logged-in username
        username = getIntent().getStringExtra("username");

        // Link UI components from sms_screen.xml
        inputPhone = findViewById(R.id.inputPhone);
        Button sendSmsButton = findViewById(R.id.sendSmsButton);
        Button back = findViewById(R.id.buttonBack);

        // Always enable back button and configure it to return to the weight tracking grid
        back.setEnabled(true);
        back.setOnClickListener(v -> finish());

        // Set click listener for send SMS button
        sendSmsButton.setOnClickListener(v -> {
            if (checkPermission()) {
                sendSms();
            } else {
                requestSmsPermission();
            }
        });
    }

    // Check if SEND_SMS permission is already granted
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Request SMS permission from user
    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            Toast.makeText(this, "SMS permission is required to send messages",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
    }

    // Send SMS message to the entered phone number
    private void sendSms() {
        String phone = inputPhone.getText().toString().trim();

        // Validate that the phone number is exactly ten digits
        if (phone.length() != 10) {
            Toast.makeText(this, "Please enter a valid phone number",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtain the system SMS manager and attempt to send SMS notification
        SmsManager smsManager = getSystemService(SmsManager.class);
        if (smsManager != null) {
            try {
                smsManager.sendTextMessage(phone, null,
                        "Hi " + username + ", You've reached your goal weight!",
                        null, null);
                Toast.makeText(this, "SMS sent successfully",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to send SMS: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "SMS Manager is not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Handles user's choice from SMS permission request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                Toast.makeText(this,
                        "SMS permission denied. The app will continue without SMS " +
                                "notifications.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
