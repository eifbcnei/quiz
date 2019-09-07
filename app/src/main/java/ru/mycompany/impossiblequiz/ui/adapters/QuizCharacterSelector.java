package ru.mycompany.impossiblequiz.ui.adapters;

import android.net.Uri;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

public interface QuizCharacterSelector {
    void onItemQuizCharacterSelected(QuizCharacter quizCharacter);
    void onSaveQuizCharacter(Uri source,String name);
}
