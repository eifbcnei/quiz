package ru.mycompany.impossiblequiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class MockDatabase {
    private static final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.applicationContext());

    private MockDatabase() {
    }


    public static int saveQuizCharacter(QuizCharacter quiz) {
       /* SharedPreferences.Editor editor = preferences.edit();
        editor.putString(String.format("#%s_STATUS", Integer.toString(index)), quizCharacter.getStatus().name());
        editor.putInt(String.format("#%s_QUESTIONS_COUNT", Integer.toString(index)), quizCharacter.getQuestionsCount());
        for (int i = 0; i < quizCharacter.getQuestionsCount(); i++) {
            Question curQuestion = quizCharacter.getQuestion(i);
            editor.putString(String.format("#%s_QUESTION_%s", Integer.toString(index), Integer.toString(i)), curQuestion.getQuestion());
        }
        editor.apply();*/
       return 0;
    }

    public static QuizCharacter getQuizCharacterByIndex(int index) {
        /**
         * until there is no real database and activity which creates quizCharacter this section should be commented
         *
         * note: saving quizCharacter after rotating will not work
         */
        /*QuizCharacter.Status status = QuizCharacter.Status.valueOf(preferences.getString(String.format("#%s_STATUS", Integer.toString(index)), QuizCharacter.Status.NORMAL.name()));
        int questionsCount = preferences.getInt(String.format("#%s_QUESTIONS_COUNT", Integer.toString(index)), 0);
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < questionsCount; i++) {
            String curQuestion = preferences.getString(String.format("#%s_QUESTION_%s", Integer.toString(index), Integer.toString(i)), "");
            List<String> answers = new ArrayList<>(preferences.getStringSet(String.format("#%s_ANSWERS_%s", Integer.toString(index), Integer.toString(i)), new HashSet<String>()));
            Question question = new Question(curQuestion, answers);
            questions.add(question);
        }
        return new QuizCharacter(status, questions);*/
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("what is my name?", Arrays.asList("Bender", "bender")));
        questions.add(new Question("where am i from?", Arrays.asList("Futurama")));
        questions.add(new Question("well done!", Arrays.asList("")));
        return new QuizCharacter(QuizCharacter.Status.NORMAL, questions);
    }
}
