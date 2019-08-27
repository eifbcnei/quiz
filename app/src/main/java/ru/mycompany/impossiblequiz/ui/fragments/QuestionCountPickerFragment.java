package ru.mycompany.impossiblequiz.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import ru.mycompany.impossiblequiz.R;

public class QuestionCountPickerFragment extends AppCompatDialogFragment {
    private InputListener listener;

    public interface InputListener {
        void onStartCreateActivity(int questionsCount);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Enter number of questions:")
                .setView(R.layout.fragment_question_count_picker)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText in = getDialog().findViewById(R.id.et_question_count);
                        try {
                            int count = Integer.parseInt(in.getText().toString());
                            listener.onStartCreateActivity(count);
                        } catch (ClassCastException cce) {
                            dismiss();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the InputListener so we can send events to the host
            listener = (InputListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement " + InputListener.class.getSimpleName());
        }
    }
}
