package ru.mycompany.impossiblequiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class AppPreferences {
    private AppPreferences() {
    }

    private static final SharedPreferences instance = PreferenceManager.getDefaultSharedPreferences(App.applicationContext());
    private static DatabaseHelper dbHelper=new DatabaseHelper(App.applicationContext());
    public static QuizCharacter getLastCharacter() {
        return dbHelper.getQuizCharacterByIndex(instance.getInt("LAST_CHARACTER_INDEX", 0));
    }

    public static void saveQuizCharacter(QuizCharacter quizCharacter){
        dbHelper.saveQuizCharacter(quizCharacter);
    }
}
