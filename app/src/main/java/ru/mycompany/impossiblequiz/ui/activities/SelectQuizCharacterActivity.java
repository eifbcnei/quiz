package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.adapters.QuizCharacterAdapter;
import ru.mycompany.impossiblequiz.ui.adapters.QuizCharacterSelector;
import ru.mycompany.impossiblequiz.viewmodels.SelectViewModel;

@EActivity(R.layout.activity_select_quiz_character)
public class SelectQuizCharacterActivity extends AppCompatActivity implements QuizCharacterSelector {
    @ViewById(R.id.rv_qc_list)
    RecyclerView recyclerView;
    @ViewById(R.id.chip_group)
    ChipGroup chipGroup;
    @ViewById
    Toolbar toolbar;
    private SelectViewModel viewModel;
    private QuizCharacterAdapter adapter;



    @AfterViews
    void initViews() {
        adapter = new QuizCharacterAdapter(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
    }

    @AfterViews
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SelectViewModel.class);
        viewModel.getQuizCharacters().observe(this, new Observer<List<QuizCharacter>>() {
            @Override
            public void onChanged(List<QuizCharacter> quizCharacters) {
                adapter.updateData(quizCharacters);
            }
        });
    }

    @Override
    public void onItemQuizCharacterSelected(QuizCharacter quizCharacter) {
        Intent data = new Intent();
        data.putExtra(QuizCharacter.class.getSimpleName(), quizCharacter);
        setResult(RESULT_OK, data);
        finish();
    }

    @AfterViews
    void initChipGroup() {
        final Chip easy = new Chip(this);
        easy.setChecked(false);
        easy.setClickable(true);
        easy.setChecked(false);
        easy.setTextColor(Color.GREEN);
        easy.setText(getString(R.string.mode_easy));
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.handleEasyMode(easy.isChecked());
            }
        });

        final Chip medium = new Chip(this);
        medium.setChecked(false);
        medium.setClickable(true);
        medium.setChecked(false);
        medium.setTextColor(Color.GREEN);
        medium.setText(getString(R.string.mode_easy));
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.handleMediumMode(medium.isChecked());
            }
        });

        final Chip extreme = new Chip(this);
        extreme.setChecked(false);
        extreme.setClickable(true);
        extreme.setChecked(false);
        extreme.setTextColor(Color.GREEN);
        extreme.setText(getString(R.string.mode_easy));
        extreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.handleExtremeMode(extreme.isChecked());
            }
        });

        chipGroup.addView(easy);
        chipGroup.addView(medium);
        chipGroup.addView(extreme);
    }
}
