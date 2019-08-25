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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.InputValidation;
import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuestionBuilder;

public class QuestionAdapter extends ArrayAdapter<QuestionBuilder> {
    private static final String BANNED_SYMBOL = "@";
    private LayoutInflater inflater;
    private List<QuestionBuilder> questionList;

    private int layout;

    public QuestionAdapter(@NonNull Context context, int resource, @NonNull List<QuestionBuilder> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        questionList = objects;
        layout = resource;
    }

    public List<Question> getUsersInput() throws IOException {
        List<Question> questions = new ArrayList<>(questionList.size());

        for (QuestionBuilder qb : questionList) {
            questions.add(qb.build());

            if (InputValidation.isQuestionValid(qb)) {
                throw new IOException();
            }
        }

        return questions;
    }

    @Nullable
    @Override
    public QuestionBuilder getItem(int position) {
        return questionList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.questionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                isInputValid(viewHolder.questionInput, true);
                questionList.get(position).setQuestion(viewHolder.questionInput.getText().toString());

            }
        });
        viewHolder.answerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                isInputValid(viewHolder.answerInput, false);
                questionList.get(position).setAnswer(viewHolder.answerInput.getText().toString());
            }
        });
        viewHolder.questionNumber.setText(String.format("Enter question #%s:", Integer.toString(position + 1)));

        return convertView;
    }

    private boolean isInputValid(EditText editText, boolean isQuestion) {
        final String input = editText.getText().toString();
        if (InputValidation.hasBannedSymbol(input)) {
            editText.setError(BANNED_SYMBOL);
            return false;
        }

        if (isQuestion && !InputValidation.isCorrectQuestion(input)) {
            editText.setError("Must end with \"?\"");
            return false;
        }

        if (InputValidation.isStringBlank(input)) {
            editText.setError("blank input");
            return false;
        }

        editText.setError(null);
        return true;
    }

    class ViewHolder {
        final TextView questionNumber;
        final EditText answerInput, questionInput;

        ViewHolder(View view) {
            questionNumber = view.findViewById(R.id.tv_question_number);
            questionInput = view.findViewById(R.id.et_question_input);
            answerInput = view.findViewById(R.id.et_answer_input);
        }
    }
}
