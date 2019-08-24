package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuestionCreator;
import ru.mycompany.impossiblequiz.ui.adapters.QuestionAdapter;
import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;

public class CreateQuizCharacterActivity extends AppCompatActivity {

    private ListView questionsList;


    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            //TODO
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz_character);


        Intent intent = getIntent();

        int questionsCount = intent.getIntExtra("QUESTIONS_COUNT", 3);

        questionsList = findViewById(R.id.lv_questions_input);

        List<QuestionCreator> list = new ArrayList<>(questionsCount);
        for (int i = 0; i < questionsCount; i++) {
            list.add(new QuestionCreator());
        }

        QuestionAdapter adapter = new QuestionAdapter(this, R.layout.question_item, list);
        questionsList.setAdapter(adapter);

        CircleImageView avatar = findViewById(R.id.civ_avatar_picker);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        FloatingActionButton saveBtn = findViewById(R.id.fab_save_character);
        saveBtn.setOnClickListener(new View.OnClickListener() {
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
