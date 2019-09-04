package ru.mycompany.impossiblequiz.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.fragments.QuestionCountPickerFragment;
import ru.mycompany.impossiblequiz.utils.Validation;
import ru.mycompany.impossiblequiz.viewmodels.QuizViewModel;

@EActivity(R.layout.activity_quiz)
@OptionsMenu(R.menu.main_menu)
public class QuizActivity extends AppCompatActivity implements QuestionCountPickerFragment.InputListener {
    private static final int CREATE_QC = 1;
    private static final int SELECT_QC = 2;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private QuizViewModel characterModel;
    @ViewById(R.id.iv_character)
    ImageView characterView;
    @ViewById(R.id.tv_question)
    TextView questionView;
    @ViewById(R.id.ib_check)
    ImageButton checkBtn;
    @ViewById(R.id.et_answer)
    EditText inputView;

    @Click(R.id.ib_check)
    void onCheckAnswer() {
        String answer = inputView.getText().toString();
        inputView.setText("");
        characterModel.onCheckAnswer(answer);
    }

    @AfterViews
    void getRequiredPermissions() {
        if (!checkPermissionForWriteExternalStorage()) requestPermissionForWriteExternalStorage();
        if (!checkPermissionForReadExternalStorage()) requestPermissionForReadExternalStorage();
    }

    public boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExternalStorage() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermissionForWriteExternalStorage() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForWriteExternalStorage() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @AfterViews
    void initViewModel() {
        characterModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        characterModel.getQuizCharacterLiveData().observe(this, new Observer<QuizCharacter>() {
            @Override
            public void onChanged(QuizCharacter quizCharacter) {
                updateQuiz(quizCharacter);
            }
        });
        characterModel.getIsQuizCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCompleted) {
                if (isCompleted)
                    Snackbar.make(inputView, getString(R.string.succes_message), Snackbar.LENGTH_LONG)
                            .setAction("Select", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onSelectQuizCharacter();
                                }
                            }).show();
            }
        });
    }


    private void updateQuiz(QuizCharacter quizCharacter) {
        questionView.setText(quizCharacter.getCurrentQuestion());
        characterView.setImageURI(characterModel.getQuizCharacterLiveData().getValue().getAvatarUri());
        QuizCharacter.Status curStatus = quizCharacter.getStatus();
        characterView.setColorFilter(Color.rgb(curStatus.red, curStatus.green, curStatus.blue), PorterDuff.Mode.MULTIPLY);
    }

    @OptionsItem(R.id.action_create_character)
    void createQC() {
        QuestionCountPickerFragment fr = new QuestionCountPickerFragment();
        fr.show(getSupportFragmentManager(), "QuestionNumberDialog");
    }

    @OptionsItem(R.id.action_select_qc)
    void onSelectQuizCharacter() {
        SelectQuizCharacterActivity_.intent(this).startForResult(SELECT_QC);
    }

    @OnActivityResult(SELECT_QC)
    void onSelected(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            QuizCharacter newQuizCharacter = data.getParcelableExtra(QuizCharacter.class.getSimpleName());
            characterModel.onNewQuizCharacterSelected(newQuizCharacter);
        }
    }

    @OnActivityResult(CREATE_QC)
    void onCreated(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            QuizCharacter newQC = data.getParcelableExtra(QuizCharacter.class.getSimpleName());
            characterModel.onNewQuizCharacterSelected(newQC);
        }
    }

    @Override
    public void onStartCreateActivity(int questionCount) {
        if (Validation.isQuestionCountValid(questionCount)) {
            CreateQuizCharacterActivity_.intent(this).extra("QUESTION_COUNT", questionCount).startForResult(CREATE_QC);
        } else {
            Toast.makeText(this, "try another count", Toast.LENGTH_LONG).show();
        }
    }
}
