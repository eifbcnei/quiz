package ru.mycompany.impossiblequiz.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;
import ru.mycompany.impossiblequiz.utils.Utils;

public class QuizCharacterAdapter extends RecyclerView.Adapter<QuizCharacterAdapter.ViewHolder> {
    private List<QuizCharacter> quizCharacters=new ArrayList<>();
    private QuizCharacterSelector selector;

    public QuizCharacterAdapter(QuizCharacterSelector selector) {
        this.selector = selector;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_character_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final QuizCharacter qc = quizCharacters.get(position);
        holder.avatar_civ.setImageURI(qc.getAvatarUri());
        holder.name_tv.setText(qc.getName());
        holder.difficulty_tv.setText(Utils.getDifficulty(qc.getQuestionCount()));
        holder.select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector.onItemQuizCharacterSelected(qc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizCharacters.size();
    }

    public void updateData(final List<QuizCharacter> newData) {
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return quizCharacters.size();
            }

            @Override
            public int getNewListSize() {
                return newData.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return quizCharacters.get(oldItemPosition) == newData.get(newItemPosition);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return quizCharacters.get(oldItemPosition).equals(newData.get(newItemPosition));
            }
        };
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        quizCharacters = newData;
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name_tv, difficulty_tv;
        final CircleImageView avatar_civ;
        final ImageButton select_btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.tv_name_value);
            difficulty_tv = itemView.findViewById(R.id.tv_difficulty_value);
            avatar_civ = itemView.findViewById(R.id.civ_qc_avatar);
            select_btn = itemView.findViewById(R.id.btn_select);
        }
    }
}
