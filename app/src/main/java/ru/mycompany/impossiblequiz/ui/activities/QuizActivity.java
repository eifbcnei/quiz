package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
        hideKeyboard();
    }

    private void hideKeyboard() {
        View focus = getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
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

    @OptionsItem(R.id.action_about)
    void aboutApp() {
        //TODO
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
