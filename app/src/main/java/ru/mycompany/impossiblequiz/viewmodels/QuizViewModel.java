package ru.mycompany.impossiblequiz.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.AppPreferences;
import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class QuizViewModel extends ViewModel {
    private final MutableLiveData<QuizCharacter> quizCharacterLiveData=new MutableLiveData<>();


    public MutableLiveData<QuizCharacter> getQuizCharacterLiveData() {
        return quizCharacterLiveData;
    }


    public void onCreate() {
        quizCharacterLiveData.setValue(AppPreferences.getLastCharacter());
    }

    public void onCheckAnswer(String answer) {
        QuizCharacter quizCharacter=quizCharacterLiveData.getValue();
        quizCharacter.checkAnswer(answer);
        quizCharacterLiveData.setValue(quizCharacter);
    }
}
