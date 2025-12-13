package com.zybooks.feero_cs_499capstone;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class ThemeHelper {

    // Method to handle which theme is selected and apply it to the preferences
    public static void applySavedTheme(AppCompatActivity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("settings",
                AppCompatActivity.MODE_PRIVATE);
        String theme = prefs.getString("theme", "System Default");

        switch (theme) {
            case "App Default":
                activity.setTheme(R.style.AppCustom_AppDefault);
                break;
            case "Light":
                activity.setTheme(R.style.AppCustom_Light);
                break;
            case "Dark":
                activity.setTheme(R.style.AppCustom_Dark);
                break;
            default:
                activity.setTheme(R.style.AppCustom_System);
                break;
        }
    }
}
