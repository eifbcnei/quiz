package ru.mycompany.impossiblequiz.repositories;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import ru.mycompany.impossiblequiz.App;
import ru.mycompany.impossiblequiz.DatabaseHelper;
import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class Repository {
    private static final Repository ourInstance = new Repository();
    private DatabaseHelper dbHelper;

    public static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
        Context mContext = App.applicationContext();
        dbHelper = new DatabaseHelper(mContext);
    }

    public void saveQuizChaaracter(QuizCharacter quizCharacter) {
        dbHelper.saveQuizCharacter(quizCharacter);
    }

    public QuizCharacter getQuizCharacter(int index) {
        return dbHelper.getQuizCharacterByIndex(index);
    }


    public @NonNull
    List<QuizCharacter> getAllQuizCharacters() {
        return dbHelper.getAllQuizCharacterData();
    }
}
