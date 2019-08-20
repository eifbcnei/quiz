package ru.mycompany.impossiblequiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.mycompany.impossiblequiz.models.Question;
import ru.mycompany.impossiblequiz.models.QuizCharacter;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String COLUMN_ID = "_id";
    private final String COLUMN_QUESTIONS = "QUESTIONS";
    private final String COLUMN_ANSWERS = "ANSWERS";
    private final String TABLE = "quizChars";
    private final String DATABASE_NAME = "quizCharacters.db";

    private final String DIVIDER = "@";

    public DatabaseHelper(Context context) {
        super(context, "quizcharacters.db", null, 1);
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

        db.execSQL(String.format("INSERT INTO quizchars VALUES (%s,%s)", questions.toString(), answers.toString()));
    }

    public QuizCharacter getQuizCharacterByIndex(int index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.rawQuery(String.format("SELECT * FROM quizchars WHERE _id=%s", Integer.toString(index)), null);
        query.moveToFirst();
        //TODO
        query.close();
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE quizchars (_id INTEGER PRIMARY KEY AUTOINCREMENT,questions TEXT,answers TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS quizchars");
        onCreate(db);
    }
}
