package ru.mycompany.impossiblequiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {
    private static final SharedPreferences instance = PreferenceManager.getDefaultSharedPreferences(App.applicationContext());
    private static final String WAS_INITIALIZED = "WAS_INITIALIZED";


    public static boolean isInitialized() {
        return instance.getBoolean(WAS_INITIALIZED, false);
    }

    public static void setWasInitialized() {
        instance.edit().putBoolean(WAS_INITIALIZED, true).apply();
    }

    private AppPreferences() {
    }
}
