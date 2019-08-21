package ru.mycompany.impossiblequiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import ru.mycompany.impossiblequiz.R;

public class CreateQuizCharacterActivity extends AppCompatActivity {

    private EditText[] questionsInput;
    private EditText[] answersInput;
    private final String BANNED_SYMBOL = "@";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz_character);

        LinearLayout linearLayout = findViewById(R.id.ll_dataInputList);

        Intent intent = getIntent();


        int questionsCount = intent.getIntExtra("QUESTIONS_COUNT", 3);

        questionsInput = new EditText[questionsCount];
        answersInput = new EditText[questionsCount];

        for (int i = 0; i < questionsCount; i++) {
            questionsInput[i] = new EditText(this);
            questionsInput[i].setHint(String.format("Question #%s", Integer.toString(i+1)));
            linearLayout.addView(questionsInput[i]);

            answersInput[i] = new EditText(this);
            answersInput[i].setHint(String.format("Answer #%s", Integer.toString(i+1)));
            linearLayout.addView(answersInput[i]);
        }

        setListeners();
    }

    private void setListeners() {
        for (final EditText et : questionsInput) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().contains(BANNED_SYMBOL)) {
                        et.setError("Invalid symbol (" + BANNED_SYMBOL + ")");
                    } else {
                        et.setError(null);
                    }
                }
            });
        }
        for (final EditText et : answersInput) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().contains(BANNED_SYMBOL)) {
                        et.setError("Invalid symbol (" + BANNED_SYMBOL + ")");
                    } else {
                        et.setError(null);
                    }
                }
            });
        }
    }
}
