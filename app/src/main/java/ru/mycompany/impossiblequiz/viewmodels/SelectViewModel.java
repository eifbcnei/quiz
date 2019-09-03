package ru.mycompany.impossiblequiz.viewmodels;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.mycompany.impossiblequiz.App;
import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.storage.Repository;
import ru.mycompany.impossiblequiz.utils.Utils;

public class SelectViewModel extends ViewModel {
    private Repository repository;
    private Context mContext;
    private List<QuizCharacter> allQuizCharacters;
    private MutableLiveData<CopyOnWriteArrayList<QuizCharacter>> visibleQuizCharacters = new MutableLiveData<>();


    public SelectViewModel() {
        repository = Repository.getInstance();
        allQuizCharacters = repository.loadAllQuizCharacters();
        CopyOnWriteArrayList<QuizCharacter> characters = new CopyOnWriteArrayList<>(allQuizCharacters);
        mContext = App.applicationContext();
        visibleQuizCharacters.setValue(characters);
    }

    public LiveData<CopyOnWriteArrayList<QuizCharacter>> getVisibleQuizCharacters() {
        return visibleQuizCharacters;
    }

    public void filterEasyMode(boolean checked) {
        Resources res = mContext.getResources();
        final String EASY = res.getString(R.string.mode_easy);
        List<QuizCharacter> visible = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : allQuizCharacters) {
            if (checked && Utils.getDifficulty(qc.getQuestionCount()).equals(EASY) && !visible.contains(qc)) {
                visible.add(qc);
            } else {
                visible.remove(qc);
            }
        }
        visibleQuizCharacters.setValue(new CopyOnWriteArrayList<>(visible));
    }

    public void filterMediumMode(boolean checked) {
        Resources res = mContext.getResources();
        final String EASY = res.getString(R.string.mode_easy);
        List<QuizCharacter> visible = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : allQuizCharacters) {
            if (checked && Utils.getDifficulty(qc.getQuestionCount()).equals(EASY) && !visible.contains(qc)) {
                visible.add(qc);
            } else {
                visible.remove(qc);
            }
        }
        visibleQuizCharacters.setValue(new CopyOnWriteArrayList<>(visible));
    }

    public void filterExtremeMode(boolean checked) {
        Resources res = mContext.getResources();
        final String EASY = res.getString(R.string.mode_easy);
        List<QuizCharacter> visible = visibleQuizCharacters.getValue();
        for (QuizCharacter qc : allQuizCharacters) {
            if (checked && Utils.getDifficulty(qc.getQuestionCount()).equals(EASY) && !visible.contains(qc)) {
                visible.add(qc);
            } else {
                visible.remove(qc);
            }
        }
        visibleQuizCharacters.setValue(new CopyOnWriteArrayList<>(visible));
    }
}
