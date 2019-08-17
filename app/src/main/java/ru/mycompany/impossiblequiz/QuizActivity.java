package ru.mycompany.impossiblequiz;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.viewmodels.CharacterViewModel;

public class QuizActivity extends AppCompatActivity {
    private String CHARACTER = "QUIZ_CHARACTER";

    private ViewModel characterModel;
    private ImageView characterView;
    private TextView questionView;
    private ImageButton checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initViews();
        initViewModel();
    }

    public void initViewModel() {
        characterModel = ViewModelProviders.of(this).get(CharacterViewModel.class);

        ((CharacterViewModel) characterModel).getQuizCharacterLiveData().observe(this, new Observer<QuizCharacter>() {
            @Override
            public void onChanged(QuizCharacter quizCharacter) {
                updateQuiz(quizCharacter);
            }
        });
    }

    private void updateQuiz(QuizCharacter quizCharacter) {
        //TODO(update ui after input answer and checking it)
    }

    private void initViews() {
        characterView = findViewById(R.id.iv_character);
        questionView = findViewById(R.id.tv_question);
        checkBtn = findViewById(R.id.ib_check);
    }

    public void onCheckAnswer(View view) {

    }
}
