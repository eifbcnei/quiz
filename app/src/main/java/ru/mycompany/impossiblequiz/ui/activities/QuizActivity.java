package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.activities.CreateQuizCharacterActivity;
import ru.mycompany.impossiblequiz.viewmodels.QuizViewModel;

public class QuizActivity extends AppCompatActivity {
    private QuizViewModel characterModel;
    private ImageView characterView;
    private TextView questionView;
    private ImageButton checkBtn;
    private EditText inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initViews();
        initViewModel();
    }

    public void initViewModel() {
        characterModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        characterModel.onCreate();

        characterModel.getQuizCharacterLiveData().observe(this, new Observer<QuizCharacter>() {
            @Override
            public void onChanged(QuizCharacter quizCharacter) {
                updateQuiz(quizCharacter);
            }
        });
    }

    private void updateQuiz(QuizCharacter quizCharacter) {
        questionView.setText(quizCharacter.getCurrentQuestion());
        QuizCharacter.Status curStatus = quizCharacter.getStatus();
        characterView.setColorFilter(Color.rgb(curStatus.red, curStatus.green, curStatus.blue), PorterDuff.Mode.MULTIPLY);
    }

    private void initViews() {
        characterView = findViewById(R.id.iv_character);
        questionView = findViewById(R.id.tv_question);
        inputView = findViewById(R.id.et_answer);
        checkBtn = findViewById(R.id.ib_check);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = inputView.getText().toString();
                inputView.setText("");
                characterModel.onCheckAnswer(answer);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.create_character:
                Intent createActivity = new Intent(this, CreateQuizCharacterActivity.class);
                createActivity.putExtra("QUESTIONS_COUNT", 6);
                startActivity(createActivity);
                break;
            case R.id.action_about:
                //TODO
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
