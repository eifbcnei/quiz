package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.ExceptionCodes;
import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.ValidationException;
import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuestionBuilder;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.models.QuizCharacterBuilder;
import ru.mycompany.impossiblequiz.ui.adapters.QuestionAdapter;
import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;
import ru.mycompany.impossiblequiz.utils.ImageUtils;
import ru.mycompany.impossiblequiz.utils.Validation;
import ru.mycompany.impossiblequiz.viewmodels.CreatorViewModel;

@EActivity(R.layout.activity_create_quiz_character)
public class CreateQuizCharacterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int IMAGE_NOT_CHOSEN = Color.RED;
    private final static int IMAGE_CHOSEN = Color.GREEN;

    private CreatorViewModel viewModel;
    @ViewById(R.id.lv_questions_input)
    ListView questionsList;
    @ViewById(R.id.fab_save_character)
    FloatingActionButton saveBtn;
    @ViewById(R.id.civ_avatar_picker)
    CircleImageView avatarCiv;
    @ViewById(R.id.et_name)
    EditText nameInput;
    @Extra("QUESTION_COUNT")
    int questions_count;

    @OnActivityResult(PICK_IMAGE)
    void onAvatarSelected(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            viewModel.onAvatarSelected(selectedImage);
        }
    }

    private Drawable getDefaultAvatar() {
        return getDrawable(R.drawable.ic_default_avatar_150dp);
    }


    @AfterViews
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CreatorViewModel.class);
        viewModel.getSelectedAvatar().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                avatarCiv.setImageURI(uri);
                if (ImageUtils.areDrawablesIdentical(ImageUtils.getDrawableFromUri(uri), getDefaultAvatar())) {
                    avatarCiv.setStrokeColor(IMAGE_NOT_CHOSEN);
                } else {
                    avatarCiv.setStrokeColor(IMAGE_CHOSEN);
                }
            }
        });
    }

    @AfterViews
    void setAvatarNotChosen() {
        avatarCiv.setStrokeColor(IMAGE_NOT_CHOSEN);
    }

    @AfterViews
    void createInputAdapter() {
        List<QuestionBuilder> list = new ArrayList<>(questions_count);
        for (int i = 0; i < questions_count; i++) {
            list.add(new QuestionBuilder());
        }

        questionsList.setAdapter(new QuestionAdapter(this, R.layout.question_item, list));
    }

    @Click(R.id.fab_save_character)
    void saveQuizCharacter() {
        List<Question> usersInput;
        QuizCharacter quizCharacter;
        try {
            checkAvatar();
            QuestionAdapter adapter = (QuestionAdapter) questionsList.getAdapter();
            usersInput = adapter.getUsersInput();
            String name = getNameInput();

            quizCharacter = new QuizCharacterBuilder()
                    .setName(name)
                    .setQuestions(usersInput)
                    .setAvatarUri(viewModel.getSelectedAvatar().getValue())
                    .createQuizCharacter();

            returnActivityResult(quizCharacter);
        } catch (ValidationException e) {
            switch (e.getCode()) {
                case INVALID_AVATAR:
                    showWarning("invalid avatar");
                    break;
                case INVALID_NAME:
                    showWarning("invalid name");
                    break;
                case INVALID_QUESTION:
                    showWarning("invalid question");
                    break;
            }
        }
    }

    @Click(R.id.civ_avatar_picker)
    void selectAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void returnActivityResult(QuizCharacter quizCharacter) {
        Intent data = new Intent();
        data.putExtra(QuizCharacter.class.getSimpleName(), quizCharacter);
        setResult(RESULT_OK, data);
        finish();
    }

    private void checkAvatar() throws ValidationException {
        if (avatarCiv.getStrokeColor() == IMAGE_NOT_CHOSEN)
            throw new ValidationException(ExceptionCodes.INVALID_AVATAR);
    }

    private String getNameInput() throws ValidationException {
        String input = nameInput.getText().toString();
        if (Validation.isStringBlank(input))
            throw new ValidationException(ExceptionCodes.INVALID_NAME);

        return input;
    }

    private void showWarning(String text) {
        Toast warning = Toast.makeText(this, text, Toast.LENGTH_LONG);
        warning.show();
    }
}
