package ru.mycompany.impossiblequiz.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

@Database(entities = {QuizCharacter.class}, version = 1)
abstract public class AppDataBase extends RoomDatabase {
    public abstract QuizCharacterDao qcDao();
}
