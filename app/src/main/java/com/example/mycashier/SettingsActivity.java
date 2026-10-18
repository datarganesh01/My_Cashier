package com.example.mycashier;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mycashier.Common.BaseActivity;
import com.example.mycashier.Common.ThemePreferenceManager;

public class SettingsActivity extends BaseActivity {

    SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        themeSwitch = findViewById(R.id.themeSwitch);

        int currentTheme = AppCompatDelegate.getDefaultNightMode();

        themeSwitch.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_YES);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            int newThemeMode = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;

            ThemePreferenceManager.saveThemeMode(this,newThemeMode);

            AppCompatDelegate.setDefaultNightMode(newThemeMode);

            recreate();
        });

    }
}