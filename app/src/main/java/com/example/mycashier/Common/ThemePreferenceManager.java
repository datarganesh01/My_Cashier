package com.example.mycashier.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePreferenceManager {

    private static final String PREF_NAME = "ThemePref";
    private static final String KEY_THEME = "theme_mode";

    public static void saveThemeMode(Context context, int themeMode)
    {

        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(KEY_THEME,themeMode).commit();
        editor.apply();

    }

    public static final int getThemeMode(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        return preferences.getInt(KEY_THEME,-1);
    }

}
