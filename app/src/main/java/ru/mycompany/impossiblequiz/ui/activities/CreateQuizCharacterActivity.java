package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
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

    public static final int PICK_IMAGE = 1;

    private ListView questionsList;
    private FloatingActionButton saveBtn;
    private CircleImageView avatarCiv;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            //TODO
        }
    }

    private ListAdapter createInputAdapter(int elementsCount) {
        List<QuestionCreator> list = new ArrayList<>(elementsCount);
        for (int i = 0; i < elementsCount; i++) {
            list.add(new QuestionCreator());

        }

        return new QuestionAdapter(this, R.layout.question_item, list);
    }

    private void initViews() {
        avatarCiv = findViewById(R.id.civ_avatar_picker);
        saveBtn = findViewById(R.id.fab_save_character);
        questionsList = findViewById(R.id.lv_questions_input);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz_character);

        initViews();

        Intent intent = getIntent();

        int questionsCount = intent.getIntExtra("QUESTIONS_COUNT", 3);


        questionsList.setAdapter(createInputAdapter(questionsCount));

        avatarCiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * validating characters image
                 * must be not null and not default -> green stroke = correct
                 * else -> red = incorrect
                 */
                if (!areDrawablesIdentical(avatarCiv.getDrawable(), getDrawable(R.drawable.ic_default_avatar_150dp))) {
                    avatarCiv.setStrokeColor(Color.GREEN);
                } else {
                    avatarCiv.setStrokeColor(Color.RED);
                }
                /**
                 * picking new image
                 */
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

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

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
