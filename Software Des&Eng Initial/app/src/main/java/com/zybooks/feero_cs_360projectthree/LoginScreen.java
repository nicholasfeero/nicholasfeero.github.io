package com.zybooks.feero_cs_360projectthree;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    // UI elements
    private EditText usernameText;
    private EditText passwordText;
    private TextView textGreeting;
    private Button buttonSubmit;

    // Database helper
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Link UI components from login_screen.xml
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        textGreeting = findViewById(R.id.textGreeting);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        Button newUser = findViewById(R.id.buttonNewUser);

        // Initialize database helper for user verification
        dbHelper = new DBHelper(this);

        // Always have new user button enabled
        newUser.setEnabled(true);

        // Redirect to create account screen when create account (newUser) button is clicked
        newUser.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, CreateAccountScreen.class);
            startActivity(intent);
        });

        // Handle login when Submit is clicked
        buttonSubmit.setOnClickListener(v -> attemptLogin());

        // Dynamically enable Submit button if username & password are not empty
        TextWatcher textWatcher = new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count,
                                                    int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = usernameText.getText().length() > 0 &&
                        passwordText.getText().length() > 0;
                buttonSubmit.setEnabled(enable);
            }
        };
        // Attach the text watcher to both input fields
        usernameText.addTextChangedListener(textWatcher);
        passwordText.addTextChangedListener(textWatcher);
    }

    // Attempt to log in using DBHelper
    private void attemptLogin() {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        // Ensures both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check entered login information against credentials in database
        if (dbHelper.checkUser(username, password)) {
            // Show greeting immediately
            textGreeting.setText(getString(R.string.returning_greeting_text, username));

            /*
            Handles navigation between login screen and weight tracking grid
            Delay navigation by 1 second (1000 ms) to allow for greeting message to display
             */
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(LoginScreen.this,
                        WeightTrackingGrid.class);
                // Pass logged-in username
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }, 1000);
        } else {
            // Displays error if login attempt fails
            Toast.makeText(this, "Invalid username or password",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
