package ru.mycompany.impossiblequiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.storage.Repository;

public class AppPreferences {
    private static final SharedPreferences instance = PreferenceManager.getDefaultSharedPreferences(App.applicationContext());
    private static final String LAST_QUIZ_CHARACTER = "LAST_CHARACTER_INDEX";
    private static final String WAS_INITED = "WAS_INITED";


    public static boolean isInited() {
        return instance.getBoolean(WAS_INITED, false);
    }

    public static void setWasInited(){
        instance.edit().putBoolean(WAS_INITED,true);
    }

    private AppPreferences() {
    }

    public static SharedPreferences getInstance() {
        return instance;
    }

    public static QuizCharacter get() {
        return Repository.getInstance().getById(instance.getInt(LAST_QUIZ_CHARACTER, 0));
    }
}
