package ru.mycompany.impossiblequiz.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.storage.Repository;

public class SelectViewModel extends ViewModel {
    private Repository repository;
    private List<QuizCharacter> allQuizCharacters;
    private MutableLiveData<CopyOnWriteArrayList<QuizCharacter>> visibleQuizCharacters = new MutableLiveData<>();


    public SelectViewModel() {
        repository = Repository.getInstance();
        allQuizCharacters = repository.loadAllQuizCharacters();
        CopyOnWriteArrayList<QuizCharacter> characters = new CopyOnWriteArrayList<>(allQuizCharacters);
        visibleQuizCharacters.setValue(characters);
    }

    public LiveData<CopyOnWriteArrayList<QuizCharacter>> getVisibleQuizCharacters() {
        return visibleQuizCharacters;
    }

    public void filterDifficultyMode(boolean checked, String difficultyMode) {
        List<QuizCharacter> visible = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : allQuizCharacters) {
            if (qc.getDifficultyMode().equals(difficultyMode)) {
                if (checked && !visible.contains(qc)) {
                    visible.add(qc);
                } else {
                    visible.remove(qc);
                }
            }
        }
        visibleQuizCharacters.setValue(new CopyOnWriteArrayList<>(visible));
    }
}
