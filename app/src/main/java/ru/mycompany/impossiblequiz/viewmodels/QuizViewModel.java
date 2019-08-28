package ru.mycompany.impossiblequiz.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class QuizViewModel extends ViewModel {
    private final MutableLiveData<QuizCharacter> quizCharacterLiveData = new MutableLiveData<>();

    public LiveData<QuizCharacter> getQuizCharacterLiveData() {
        return quizCharacterLiveData;
    }

    public void onNewQuizCharacterSelected(QuizCharacter quizCharacter) {
        quizCharacterLiveData.setValue(quizCharacter);
    }

    public void onCheckAnswer(String answer) {
        QuizCharacter quizCharacter = quizCharacterLiveData.getValue();
        quizCharacter.checkAnswer(answer);
        quizCharacterLiveData.setValue(quizCharacter);
    }
}
