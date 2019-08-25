package ru.mycompany.impossiblequiz.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuestionBuilder;
import ru.mycompany.impossiblequiz.ui.adapters.QuestionAdapter;
import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;
import ru.mycompany.impossiblequiz.viewmodels.QuizViewModel;

public class CreateQuizCharacterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int IMAGE_NOT_CHOSEN = Color.RED;
    private static final int IMAGE_CHOSEN = Color.GREEN;


    private QuizViewModel characterViewModel;
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
                    characterViewModel.onAvatarChanged(selectedImage,defaultAvatar);
                    break;
            }
    }

    private void initViewModel() {
        characterViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        characterViewModel.onCIVinit(avatarCiv);
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
                if (avatarCiv.getStrokeColor() == IMAGE_NOT_CHOSEN) {
                    showWarning(getString(R.string.image_warning));
                }

                QuestionAdapter adapter = (QuestionAdapter) questionsList.getAdapter();
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    QuestionBuilder item = adapter.getItem(i);

                }
            }
        });
    }

    private void showWarning(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }


}
