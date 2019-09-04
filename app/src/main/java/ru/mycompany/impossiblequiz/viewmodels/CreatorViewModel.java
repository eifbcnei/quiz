package ru.mycompany.impossiblequiz.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.storage.Repository;

public class CreatorViewModel extends ViewModel {
    private MutableLiveData<Uri> selectedAvatar = new MutableLiveData<>();

    public void onAvatarSelected(Uri newAvatar) {
        selectedAvatar.setValue(newAvatar);
    }

    public LiveData<Uri> getSelectedAvatar() {
        return selectedAvatar;
    }

    public void saveData(QuizCharacter quizCharacter) {
        Repository.getInstance().insert(quizCharacter);
    }
}
