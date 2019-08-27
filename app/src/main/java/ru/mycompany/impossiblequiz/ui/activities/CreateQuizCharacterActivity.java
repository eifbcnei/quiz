package ru.mycompany.impossiblequiz.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import ru.mycompany.impossiblequiz.utils.Validation;
import ru.mycompany.impossiblequiz.viewmodels.CreatorViewModel;

public class CreateQuizCharacterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int IMAGE_NOT_CHOSEN = Color.RED;

    private CreatorViewModel viewModel;
    private ListView questionsList;
    private FloatingActionButton saveBtn;
    private CircleImageView avatarCiv;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case PICK_IMAGE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    Drawable defaultAvatar = getDrawable(R.drawable.ic_default_avatar_150dp);
                    viewModel.onAvatarChanged(selectedImage, defaultAvatar);
                    break;
            }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CreatorViewModel.class);
        viewModel.onInit(avatarCiv);
    }

    private ListAdapter createInputAdapter(int elementsCount) {
        List<QuestionBuilder> list = new ArrayList<>(elementsCount);
        for (int i = 0; i < elementsCount; i++) {
            list.add(new QuestionBuilder());
        }

        return new QuestionAdapter(this, R.layout.question_item, list);
    }

    private void initViews() {
        avatarCiv = findViewById(R.id.civ_avatar_picker);
        avatarCiv.setStrokeColor(IMAGE_NOT_CHOSEN);
        saveBtn = findViewById(R.id.fab_save_character);
        questionsList = findViewById(R.id.lv_questions_input);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz_character);

        initViews();
        initViewModel();

        Intent intent = getIntent();

        int questionsCount = intent.getIntExtra("QUESTIONS_COUNT", 3);


        questionsList.setAdapter(createInputAdapter(questionsCount));

        avatarCiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            .setAvatarUri(viewModel.getAvatarUri())
                            .createQuizCharacter();
                    returnActivityResult(quizCharacter);
                } catch (ValidationException e) {
                    switch (e.getCode()) {
                        case INVALID_NAME:
                            showWarning("invalid name");
                            break;
                        case INVALID_AVATAR:
                            showWarning("invalid avatar");
                            break;
                        case INVALID_QUESTION:
                            showWarning("invalid question");
                            break;
                    }
                }
            }
        });
    }

    private void returnActivityResult(QuizCharacter quizCharacter) {
        Intent data = new Intent();
        data.putExtra(QuizCharacter.class.getSimpleName(), quizCharacter);
        setResult(RESULT_OK, data);
        finish();
    }

    private void checkAvatar() throws ValidationException {
        if (viewModel.getStrokeColor() == IMAGE_NOT_CHOSEN)
            throw new ValidationException(ExceptionCodes.INVALID_AVATAR);
    }

    private String getNameInput() throws ValidationException {
        EditText nameInput = findViewById(R.id.et_name);
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
