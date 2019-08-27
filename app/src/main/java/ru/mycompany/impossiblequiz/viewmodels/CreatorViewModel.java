package ru.mycompany.impossiblequiz.viewmodels;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;
import ru.mycompany.impossiblequiz.utils.ImageUtils;

public class CreatorViewModel extends ViewModel {
    private MutableLiveData<CircleImageView> civ = new MutableLiveData<>();


    private static final int IMAGE_NOT_CHOSEN = Color.RED;
    private static final int IMAGE_CHOSEN = Color.GREEN;

    public void onAvatarChanged(Uri newAvatar, Drawable defaultAvatar) {
        CircleImageView cc = civ.getValue();
        cc.setImageURI(newAvatar);
        if (!ImageUtils.areDrawablesIdentical(cc.getDrawable(), defaultAvatar)) {
            cc.setStrokeColor(IMAGE_CHOSEN);
        } else {
            cc.setStrokeColor(IMAGE_NOT_CHOSEN);
        }
        civ.setValue(cc);
    }

    public int getStrokeColor() {
        return civ.getValue().getStrokeColor();
    }

    public void onInit(CircleImageView circleImageView) {
        civ.setValue(circleImageView);
    }

    public Uri getAvatarUri() {
        return civ.getValue().getmImageUri();
    }


}
