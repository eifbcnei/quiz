package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.ui.adapters.QuestionAdapter;
import ru.mycompany.impossiblequiz.models.QuestionCreator;

public class CreateQuizCharacterActivity extends AppCompatActivity {

    private ListView questionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz_character);


        Intent intent = getIntent();

        int questionsCount = intent.getIntExtra("QUESTIONS_COUNT", 3);

        questionsList = findViewById(R.id.ll_dataInputList);

        List<QuestionCreator> list = new ArrayList<>(questionsCount);
        for (int i = 0; i < questionsCount; i++) {
            list.add(new QuestionCreator());
        }

        QuestionAdapter adapter = new QuestionAdapter(this, R.layout.question_item, list);
        questionsList.setAdapter(adapter);

        Button btn = findViewById(R.id.btn_save_character);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = questionsList.getAdapter().getCount();
                for (int i = 0; i < count; i++) {
                    //TODO save new quiz character and set as current in view model
                }
            }
        });
    }

}
