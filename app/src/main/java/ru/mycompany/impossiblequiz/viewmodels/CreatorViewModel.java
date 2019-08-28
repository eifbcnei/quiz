package ru.mycompany.impossiblequiz.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreatorViewModel extends ViewModel {
    private MutableLiveData<Uri> selectedAvatar = new MutableLiveData<>();

    public void onAvatarSelected(Uri newAvatar) {
        selectedAvatar.setValue(newAvatar);
    }

    public LiveData<Uri> getSelectedAvatar() {
        return selectedAvatar;
    }
}
