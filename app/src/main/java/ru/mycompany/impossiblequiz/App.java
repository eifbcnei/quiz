package ru.mycompany.impossiblequiz;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import ru.mycompany.impossiblequiz.storage.AppDataBase;

public class App extends Application {
    private static App instance = null;
    private AppDataBase dataBase;

    public static Context applicationContext() {
        return instance.getApplicationContext();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, "impossible_quiz").build();
    }
}
