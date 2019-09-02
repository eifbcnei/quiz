package ru.mycompany.impossiblequiz.viewmodels;

import android.content.res.Resources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.repositories.Repository;
import ru.mycompany.impossiblequiz.utils.Utils;

public class SelectViewModel extends ViewModel {
    private Repository repository = Repository.getInstance();

    private MutableLiveData<List<QuizCharacter>> quizCharacters = new MutableLiveData<>();
    private MutableLiveData<List<QuizCharacter>> visibleQuizCharacters = new MutableLiveData<>();

    public SelectViewModel() {
        List<QuizCharacter> quizCharacterList = repository.getAllQuizCharacters();
        Collections.sort(quizCharacterList, new Comparator<QuizCharacter>() {
            @Override
            public int compare(QuizCharacter o1, QuizCharacter o2) {
                return o1.getQuestionCount() - o2.getQuestionCount();
            }
        });
        quizCharacters.setValue(quizCharacterList);
        visibleQuizCharacters.setValue(quizCharacters.getValue());
    }

    private void updateQuizCharactersList() {
        List<QuizCharacter> quizCharacterList = visibleQuizCharacters.getValue();
        Collections.sort(quizCharacterList, new Comparator<QuizCharacter>() {
            @Override
            public int compare(QuizCharacter o1, QuizCharacter o2) {
                return o1.getQuestionCount() - o2.getQuestionCount();
            }
        });
        visibleQuizCharacters.setValue(quizCharacterList);
    }

    public LiveData<List<QuizCharacter>> getQuizCharacters() {
        return quizCharacters;
    }

    public void handleEasyMode(boolean checked) {
        final String EASY = Resources.getSystem().getString(R.string.mode_easy);
        List<QuizCharacter> qcList = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : quizCharacters.getValue()) {
            if (Utils.getDifficulty(qc.getQuestionCount()) == EASY) {
                if (checked) {
                    qcList.remove(qc);
                } else {
                    qcList.add(qc);
                }
            }
        }
        visibleQuizCharacters.setValue(qcList);
    }

    public void handleMediumMode(boolean checked) {
        final String MEDIUM = Resources.getSystem().getString(R.string.mode_medium);
        List<QuizCharacter> qcList = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : quizCharacters.getValue()) {
            if (Utils.getDifficulty(qc.getQuestionCount()) == MEDIUM) {
                if (checked) {
                    qcList.remove(qc);
                } else {
                    qcList.add(qc);
                }
            }
        }
        visibleQuizCharacters.setValue(qcList);
    }

    public void handleExtremeMode(boolean checked) {
        final String EXTREME = Resources.getSystem().getString(R.string.mode_extreme);
        List<QuizCharacter> qcList = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : quizCharacters.getValue()) {
            if (Utils.getDifficulty(qc.getQuestionCount()) == EXTREME) {
                if (checked) {
                    qcList.remove(qc);
                } else {
                    qcList.add(qc);
                }
            }
        }
        visibleQuizCharacters.setValue(qcList);
    }
}
