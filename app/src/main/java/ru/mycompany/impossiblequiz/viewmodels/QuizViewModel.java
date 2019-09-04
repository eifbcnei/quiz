package ru.mycompany.impossiblequiz.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.storage.Repository;

public class QuizViewModel extends ViewModel {
    private final MutableLiveData<QuizCharacter> quizCharacterLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isQuizCompleted = new MutableLiveData<>();

    public QuizViewModel() {
        isQuizCompleted.setValue(false);
        quizCharacterLiveData.setValue(Repository.getInstance().getById(0));
    }

    public MutableLiveData<Boolean> getIsQuizCompleted() {
        return isQuizCompleted;
    }

    public LiveData<QuizCharacter> getQuizCharacterLiveData() {
        return quizCharacterLiveData;
    }

    public void onNewQuizCharacterSelected(QuizCharacter quizCharacter) {
        quizCharacterLiveData.setValue(quizCharacter);
    }

    public void onCheckAnswer(String answer) {
        QuizCharacter quizCharacter = quizCharacterLiveData.getValue();
        quizCharacter.checkAnswer(answer);
        if (quizCharacter.getQuestionCount() == quizCharacter.getCurQuestionIndex()) {
            isQuizCompleted.setValue(true);
        }
        quizCharacterLiveData.setValue(quizCharacter);
    }
}
