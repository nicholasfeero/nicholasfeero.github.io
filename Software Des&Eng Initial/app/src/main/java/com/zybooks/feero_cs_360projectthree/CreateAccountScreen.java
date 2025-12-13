package com.zybooks.feero_cs_360projectthree;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountScreen extends AppCompatActivity {

    // UI elements
    private EditText usernameText;
    private EditText passwordText;
    private Button createAccountButton;

    // Database helper
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_screen);

        // Link UI components from create_account_screen.xml
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        createAccountButton = findViewById(R.id.buttonNewUser);
        Button backButton = findViewById(R.id.buttonBack);

        // Initialize DB helper for user account database storage
        dbHelper = new DBHelper(this);

        // Navigate back to the login screen by clicking the back button
        backButton.setEnabled(true);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(CreateAccountScreen.this, LoginScreen.class));
            finish();
        });

        // Enable create account button only when both username and password fields are filled
        TextWatcher fieldWatcher = new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count,
                                                    int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = usernameText.getText().length() > 0 &&
                        passwordText.getText().length() > 0;
                createAccountButton.setEnabled(enable);
            }
        };
        // Attach the text watcher to both input fields
        usernameText.addTextChangedListener(fieldWatcher);
        passwordText.addTextChangedListener(fieldWatcher);

        // Handle account creation when create account button is clicked
        createAccountButton.setOnClickListener(v -> {
            String username = usernameText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            // Check if username exists in database
            if (dbHelper.userExists(username)) {
                Toast.makeText(this, "Username already exists",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Attempt to enter the new user into the database
                boolean success = dbHelper.insertUser(username, password);
                if (success) {
                    Toast.makeText(this, "Account created successfully",
                            Toast.LENGTH_SHORT).show();
                    // Return to login screen on successful entry
                    startActivity(new Intent(CreateAccountScreen.this,
                            LoginScreen.class));
                    finish();
                } else {
                    // Display error message if account creation fails
                    Toast.makeText(this, "Error creating account",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
