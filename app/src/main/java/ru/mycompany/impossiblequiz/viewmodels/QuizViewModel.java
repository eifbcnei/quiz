package ru.mycompany.impossiblequiz.viewmodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mycompany.impossiblequiz.AppPreferences;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;

public class QuizViewModel extends ViewModel {
    private final MutableLiveData<QuizCharacter> quizCharacterLiveData = new MutableLiveData<>();
    private final MutableLiveData<CircleImageView> avatarLiveData = new MutableLiveData<>();

    private static final int IMAGE_NOT_CHOSEN = Color.RED;
    private static final int IMAGE_CHOSEN = Color.GREEN;

    public void onAvatarChanged(Uri newAvatar, Drawable defaultAvatar) {
        CircleImageView civ = avatarLiveData.getValue();
        civ.setImageURI(newAvatar);
        if (!areDrawablesIdentical(civ.getDrawable(), defaultAvatar)) {
            civ.setStrokeColor(IMAGE_CHOSEN);
        } else {
            civ.setStrokeColor(IMAGE_NOT_CHOSEN);
        }
        avatarLiveData.setValue(civ);
    }

    public void onCIVinit(CircleImageView circleImageView){
        avatarLiveData.setValue(circleImageView);
    }

    public LiveData<QuizCharacter> getQuizCharacterLiveData() {
        return quizCharacterLiveData;
    }

    public void onNewQuizCharacterSelected(QuizCharacter quizCharacter) {
        quizCharacterLiveData.setValue(quizCharacter);
    }


    public void onCreate() {
        quizCharacterLiveData.setValue(AppPreferences.getLastCharacter());

    }

    public void onCheckAnswer(String answer) {
        QuizCharacter quizCharacter = quizCharacterLiveData.getValue();
        quizCharacter.checkAnswer(answer);
        quizCharacterLiveData.setValue(quizCharacter);
    }

    private static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    private static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
