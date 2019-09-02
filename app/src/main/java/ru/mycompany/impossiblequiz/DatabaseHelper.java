package ru.mycompany.impossiblequiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuizCharacter;
import ru.mycompany.impossiblequiz.models.QuizCharacterBuilder;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_QUESTIONS = "QUESTIONS";
    private static final String COLUMN_ANSWERS = "ANSWERS";
    private static final String TABLE = "quizChars";
    private static final String DATABASE_NAME = "quizCharacters.db";
    private static final String COLUMN_AVATAR_URI = "URI";

    private final String DIVIDER = "@";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void saveQuizCharacter(QuizCharacter quizCharacter) {
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder questions = new StringBuilder();
        StringBuilder answers = new StringBuilder();

        for (Question question : quizCharacter.getQuestions()) {
            questions.append(question.getQuestion());
            questions.append(DIVIDER);
            answers.append(question.getAnswer());
            answers.append(DIVIDER);
        }
        answers.deleteCharAt(answers.lastIndexOf(DIVIDER));
        questions.deleteCharAt(questions.lastIndexOf(DIVIDER));

        db.execSQL(String.format("INSERT INTO " + TABLE + " VALUES (%s,%s)", questions.toString(), answers.toString()));
    }

    public QuizCharacter getQuizCharacterByIndex(int index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.rawQuery(String.format("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=%s", Integer.toString(index)), null);
        query.moveToFirst();
        String name = query.getColumnName(1);
        List<String> questions = new ArrayList<>(Arrays.asList(query.getColumnName(2).split(DIVIDER)));
        List<String> answers = new ArrayList<>(Arrays.asList(query.getColumnName(3).split(DIVIDER)));
        List<Question> questionList = new ArrayList<>();
        Uri uri = Uri.parse(query.getColumnName(4));
        for (int i = 0; i != questions.size(); i++) {
            questionList.add(new Question(questions.get(i), answers.get(i)));
        }
        QuizCharacter quizCharacter = new QuizCharacterBuilder()
                .setName(name)
                .setQuestions(questionList)
                .setAvatarUri(uri)
                .createQuizCharacter();
        query.close();
        return quizCharacter;
    }

    public List<QuizCharacter> getAllQuizCharacterData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + TABLE, null);
        List<QuizCharacter> quizCharacters = new ArrayList<>();
        while (query.moveToNext()) {
            String name = query.getColumnName(1);
            List<String> questions = new ArrayList<>(Arrays.asList(query.getColumnName(2).split(DIVIDER)));
            List<String> answers = new ArrayList<>(Arrays.asList(query.getColumnName(3).split(DIVIDER)));
            List<Question> questionList = new ArrayList<>();
            Uri uri = Uri.parse(query.getColumnName(4));
            for (int i = 0; i != questions.size(); i++) {
                questionList.add(new Question(questions.get(i), answers.get(i)));
            }
            QuizCharacter quizCharacter = new QuizCharacterBuilder()
                    .setName(name)
                    .setQuestions(questionList)
                    .setAvatarUri(uri)
                    .createQuizCharacter();
            quizCharacters.add(quizCharacter);
        }
        query.close();
        return quizCharacters;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_QUESTIONS + " TEXT,"
                + COLUMN_ANSWERS + " TEXT,"
                + COLUMN_AVATAR_URI + " TEXT);");

        initDefaultQuizCharacters();
    }

    private void initDefaultQuizCharacters() {
        SQLiteDatabase db = getWritableDatabase();
        //TODO
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
