package ru.mycompany.impossiblequiz.storage;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.mycompany.impossiblequiz.App;
import ru.mycompany.impossiblequiz.AppPreferences;
import ru.mycompany.impossiblequiz.R;
import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.models.QuizCharacterBuilder;
import ru.mycompany.impossiblequiz.utils.ImageUtils;

public class Repository {
    private static AppDataBase dataBase;
    private final static Repository repository = new Repository();
    private List<QuizCharacter> cache = new ArrayList<>();

    public static Repository getInstance() {
        return repository;
    }

    private Repository() {
        dataBase = App.getInstance().getDataBase();
        if (!AppPreferences.isInited()) {
            defaultInit();
            AppPreferences.setWasInited();
        }
    }

    public QuizCharacter getById(int id) {
        return cache.get(id);
    }

    public List<QuizCharacter> loadAllQuizCharacters() {
        try {
            cache = new Loader().execute().get();
            return cache;

        } catch (ExecutionException e) {
            return null;
        } catch (InterruptedException e) {
            return null;
        }
    }

    private void defaultInit() {
        QuizCharacter[] quizCharacters = {new QuizCharacterBuilder()
                .setName("Giorno Giovana")
                .setAvatarUri(ImageUtils.getUriToDrawable(R.drawable.giorno))
                .addQuestion(new Question("What is my name?", "Giorno"))
                .addQuestion(new Question("Who is my father?", "Dio"))
                .addQuestion(new Question("Who am i?", "Boss"))
                .createQuizCharacter()};
        cache.addAll(Arrays.asList(quizCharacters));
        new Saver().execute(quizCharacters);
    }

    public void insert(QuizCharacter quizCharacter) {
        new Saver().execute(quizCharacter);
    }

    private static class Loader extends AsyncTask<Void, Void, List<QuizCharacter>> {
        @Override
        protected List<QuizCharacter> doInBackground(Void... voids) {
            return dataBase.qcDao().getAll();
        }
    }

    private static class Saver extends AsyncTask<QuizCharacter, Void, Void> {
        @Override
        protected Void doInBackground(QuizCharacter... quizCharacters) {
            for (QuizCharacter quizCharacter : quizCharacters) {
                dataBase.qcDao().insert(quizCharacter);
            }
            return null;
        }
    }
}
