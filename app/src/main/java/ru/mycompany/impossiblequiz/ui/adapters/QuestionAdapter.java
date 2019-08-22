package ru.mycompany.impossiblequiz.ui.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuestionCreator;

public class QuestionAdapter extends ArrayAdapter<QuestionCreator> {
    private LayoutInflater inflater;
    private List<QuestionCreator> questionList;
    private int layout;

    public QuestionAdapter(@NonNull Context context, int resource, @NonNull List<QuestionCreator> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        questionList = objects;
        layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView questionNumber = view.findViewById(R.id.tv_question_number);
        final EditText questionInput = view.findViewById(R.id.et_question_input);
        EditText answerInput = view.findViewById(R.id.et_answer_input);
        TextWatcher inputTester = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("@")) {
                    questionInput.setError("@");
                } else {
                    questionInput.setError(null);
                }
            }
        };

        questionInput.addTextChangedListener(inputTester);
        answerInput.addTextChangedListener(inputTester);

        questionNumber.setText(String.format("Enter question #%s:", Integer.toString(position)));

        return view;
    }


}
