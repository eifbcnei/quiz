package ru.mycompany.impossiblequiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class AppPreferences {
    private AppPreferences() {
    }

    private static final SharedPreferences instance = PreferenceManager.getDefaultSharedPreferences(App.applicationContext());

    public static QuizCharacter getLastCharacter() {
        return MockDatabase.getQuizCharacterByIndex(instance.getInt("LAST_CHARACTER_INDEX", 0));
    }

    public static void saveQuizCharacter(QuizCharacter quizCharacter){
        int index= MockDatabase.saveQuizCharacter(quizCharacter);
        instance.edit().putInt("LAST_CHARCATER_INDEX",index);
    }
}
