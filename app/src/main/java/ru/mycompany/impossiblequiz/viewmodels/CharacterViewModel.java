package ru.mycompany.impossiblequiz.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class CharacterViewModel extends ViewModel {
    private LiveData<QuizCharacter> quizCharacterLiveData;

public LiveData<QuizCharacter> getQuizCharacterLiveData(){
    return quizCharacterLiveData;
}

}
