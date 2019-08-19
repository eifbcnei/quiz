package ru.mycompany.impossiblequiz;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App instance = null;

    public static Context applicationContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
