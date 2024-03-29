package ru.mycompany.impossiblequiz.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.exceptions.ExceptionCodes;
import ru.mycompany.impossiblequiz.exceptions.ValidationException;
import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuestionBuilder;
import ru.mycompany.impossiblequiz.utils.Validation;

public class QuestionAdapter extends ArrayAdapter<QuestionBuilder> {
    private static final String BANNED_SYMBOL = "@";
    private LayoutInflater inflater;
    private List<QuestionBuilder> questionList;
    private Resources resources;
    private int layout;

    public QuestionAdapter(@NonNull Context context, int resource, @NonNull List<QuestionBuilder> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        questionList = objects;
        resources = context.getResources();
        layout = resource;
    }

    public List<Question> getUsersInput() throws ValidationException {
        List<Question> questions = new ArrayList<>(questionList.size());

        for (QuestionBuilder qb : questionList) {
            questions.add(qb.build());

            if (!Validation.isQuestionValid(qb)) {
                throw new ValidationException(ExceptionCodes.INVALID_QUESTION);
            }
        }
        return questions;
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

        final QuestionBuilder qb = questionList.get(position);

        TextAnalyser mtw = (TextAnalyser) viewHolder.questionInput.getTag();
        if (mtw != null) viewHolder.questionInput.removeTextChangedListener(mtw);

        mtw = (TextAnalyser) viewHolder.answerInput.getTag();
        if (mtw != null) viewHolder.answerInput.removeTextChangedListener(mtw);

        viewHolder.answerInput.setText(qb.answer);
        viewHolder.questionInput.setText(qb.question);

        TextAnalyser questionAnalyser = new TextAnalyser(viewHolder.questionInput, true, position);
        viewHolder.questionInput.addTextChangedListener(questionAnalyser);
        viewHolder.questionInput.setTag(questionAnalyser);

        TextAnalyser answerAnalyser = new TextAnalyser(viewHolder.answerInput, false, position);
        viewHolder.answerInput.addTextChangedListener(answerAnalyser);
        viewHolder.answerInput.setTag(answerAnalyser);

        viewHolder.questionNumber.setText(String.format(resources.getString(R.string.question_number), Integer.toString(position + 1)));

        return convertView;
    }

    class TextAnalyser implements TextWatcher {
        EditText editText;
        boolean isQuestion;
        int position;

        public TextAnalyser(EditText editText, boolean isQuestion, int position) {
            this.editText = editText;
            this.position = position;
            this.isQuestion = isQuestion;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            isInputValid(editText, isQuestion);
            QuestionBuilder qb = questionList.get(position);
            if (isQuestion) {
                qb.setQuestion(editText.getText().toString());
            } else {
                qb.setAnswer(editText.getText().toString());
            }
        }
    }

    private void isInputValid(EditText editText, boolean isQuestion) {
        final String input = editText.getText().toString();
        if (Validation.hasBannedSymbol(input)) {
            editText.setError(BANNED_SYMBOL);
            return;
        }

        if (isQuestion && !Validation.isCorrectQuestion(input)) {
            editText.setError(resources.getString(R.string.question_ending_error));
            return;
        }

        if (Validation.isStringBlank(input)) {
            editText.setError(resources.getString(R.string.blank_input_error));
            return;
        }

        editText.setError(null);
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
