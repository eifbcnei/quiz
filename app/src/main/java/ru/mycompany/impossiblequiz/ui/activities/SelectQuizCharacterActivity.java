package ru.mycompany.impossiblequiz.ui.activities;

import android.content.Intent;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.CopyOnWriteArrayList;

import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.ui.adapters.QuizCharacterAdapter;
import ru.mycompany.impossiblequiz.ui.adapters.QuizCharacterSelector;
import ru.mycompany.impossiblequiz.viewmodels.SelectViewModel;

@EActivity(R.layout.activity_select_quiz_character)
public class SelectQuizCharacterActivity extends AppCompatActivity implements QuizCharacterSelector{
    @ViewById(R.id.rv_qc_list)
    RecyclerView recyclerView;

    private SelectViewModel viewModel;
    private QuizCharacterAdapter adapter;

    @AfterViews
    void initAdapter() {
        adapter = new QuizCharacterAdapter(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
    }

    @AfterViews
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SelectViewModel.class);
        viewModel.getVisibleQuizCharacters().observe(this, new Observer<CopyOnWriteArrayList<QuizCharacter>>() {
            @Override
            public void onChanged(CopyOnWriteArrayList<QuizCharacter> quizCharacters) {
                adapter.updateData(quizCharacters);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @AfterViews
    void initChips() {
        Chip easy = findViewById(R.id.chip_mode_easy);
        easy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.filterEasyMode(isChecked);
            }
        });
        Chip medium = findViewById(R.id.chip_mode_medium);
        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.filterMediumMode(isChecked);
            }
        });
        Chip extreme = findViewById(R.id.chip_mode_extreme);
        extreme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.filterExtremeMode(isChecked);
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



}
