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
                updateError(viewHolder.questionInput, s.toString());
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
                updateError(viewHolder.answerInput, s.toString());
            }
        });
        viewHolder.questionNumber.setText(String.format("Enter question #%s:", Integer.toString(position + 1)));

        return convertView;
    }

    class ViewHolder {
        final TextView questionNumber;
        final EditText answerInput, questionInput;

        public ViewHolder(View view) {
            questionNumber = view.findViewById(R.id.tv_question_number);
            questionInput = view.findViewById(R.id.et_question_input);
            answerInput = view.findViewById(R.id.et_answer_input);
        }
    }

    private void updateError(EditText editText, String input) {
        final String BANNED_SYMBOL = "@";
        if (input.contains(BANNED_SYMBOL)) {
            editText.setError("@");
        } else {
            editText.setError(null);
        }
    }
}
