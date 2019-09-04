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
        if (!AppPreferences.isInitialized()) {
            defaultInit();
            AppPreferences.setWasInitialized();
        }
    }

    public QuizCharacter getById(int id) {
        if (!(cache.size() > id)) return new QuizCharacterBuilder()
                .setName("Squidward")
                .setAvatarUri(ImageUtils.getResIdToDrawable(R.drawable.squidward))
                .addQuestion(new Question("Who is the most despicable neighbour?", "Sponge Bob"))
                .addQuestion(new Question("Who is the second most despicable neighbour?", "Patrick"))
                .addQuestion(new Question("What is the best thing in the world?", "Art"))
                .createQuizCharacter();

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
        QuizCharacter[] quizCharacters = {
                new QuizCharacterBuilder()
                        .setName("Morty Smith")
                        .setAvatarUri(ImageUtils.getResIdToDrawable(R.drawable.morty))
                        .addQuestion(new Question("Who is my grandpa?", "Rick"))
                        .addQuestion(new Question("What universe am i from?", "C136"))
                        .addQuestion(new Question("What universe do i live now?", "C137"))
                        .createQuizCharacter(),
                new QuizCharacterBuilder()
                        .setName("Bender")
                        .setAvatarUri(ImageUtils.getResIdToDrawable(R.drawable.bender))
                        .addQuestion(new Question("Who is my best friend", "Fry"))
                        .addQuestion(new Question("What is my serial number?", "2716057"))
                        .addQuestion(new Question("What year was i built", "2993"))
                        .createQuizCharacter(),
                new QuizCharacterBuilder()
                        .setName("Squidward")
                        .setAvatarUri(ImageUtils.getResIdToDrawable(R.drawable.squidward))
                        .addQuestion(new Question("Who is the most despicable neighbour?", "Sponge Bob"))
                        .addQuestion(new Question("Who is the second most despicable neighbour?", "Patrick"))
                        .addQuestion(new Question("What is the best thing in the world?", "Art"))
                        .createQuizCharacter(),
                new QuizCharacterBuilder()
                        .setName("Giorno Giovanna")
                        .setAvatarUri(ImageUtils.getResIdToDrawable(R.drawable.giorno))
                        .addQuestion(new Question("Who is my father?", "Dio"))
                        .addQuestion(new Question("What do i hate the most?", "Drugs"))
                        .addQuestion(new Question("What is my stand name?", "Gold experience"))
                        .addQuestion(new Question("Who am i?", "Boss"))
                        .addQuestion(new Question("Who is my favourite musician?", "Jeff Beck"))
                        .createQuizCharacter(),
                new QuizCharacterBuilder()
                        .setName("Homer Simpson")
                        .setAvatarUri(ImageUtils.getResIdToDrawable(R.drawable.homer))
                        .addQuestion(new Question("Who is Marge for me?", "Wife"))
                        .addQuestion(new Question("How many children do i have?", "3"))
                        .addQuestion(new Question("Where do i live?", "Springfield"))
                        .addQuestion(new Question("What is my favourite beer?", "Duff"))
                        .addQuestion(new Question("How old am i?", "38"))
                        .addQuestion(new Question("What is my first episode?", "Good Night"))
                        .createQuizCharacter()
        };
        cache.addAll(Arrays.asList(quizCharacters));
        new Saver().execute(quizCharacters);
    }

    public void insert(QuizCharacter quizCharacter) {
        new Saver().execute(quizCharacter);
    }

    private class Loader extends AsyncTask<Void, Void, List<QuizCharacter>> {
        @Override
        protected List<QuizCharacter> doInBackground(Void... voids) {
            cache = dataBase.qcDao().getAll();
            return cache;
        }
    }

    private class Saver extends AsyncTask<QuizCharacter, Void, Void> {
        @Override
        protected Void doInBackground(QuizCharacter... quizCharacters) {
            for (QuizCharacter quizCharacter : quizCharacters) {
                dataBase.qcDao().insert(quizCharacter);
            }
            return null;
        }
    }
}
