package ru.mycompany.impossiblequiz.ui.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mycompany.impossiblequiz.App;
import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.custom.CircleImageView;
import ru.mycompany.impossiblequiz.utils.ImageUtils;
import ru.mycompany.impossiblequiz.utils.Utils;

public class QuizCharacterAdapter extends RecyclerView.Adapter<QuizCharacterAdapter.ViewHolder> {
    private List<QuizCharacter> quizCharacters = new ArrayList<>();
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
        holder.name_tv.setText(Long.toString(qc.getId()));
        holder.difficulty_tv.setText(Utils.getDifficulty(qc.getQuestionCount()));
        holder.select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector.onItemQuizCharacterSelected(qc);
            }
        });
        final Uri source = holder.avatar_civ.getmImageUri();
        holder.save_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(not working :D)
                Bitmap bitmapImage = ImageUtils.getBitmap(ImageUtils.getDrawableFromUri(source));
                ContextWrapper cw = new ContextWrapper(App.applicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File mypath = new File(directory, qc.getName().replaceAll(" ", "_") + ".jpg");

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizCharacters.size();
    }

    public void updateData(final List<QuizCharacter> newData) {
        quizCharacters = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name_tv, difficulty_tv;
        final CircleImageView avatar_civ;
        final ImageButton select_btn, save_image_btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.tv_name_value);
            difficulty_tv = itemView.findViewById(R.id.tv_difficulty_value);
            avatar_civ = itemView.findViewById(R.id.civ_qc_avatar);
            select_btn = itemView.findViewById(R.id.btn_select);
            save_image_btn = itemView.findViewById(R.id.btn_save_image);
        }
    }
}
