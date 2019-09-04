package ru.mycompany.impossiblequiz.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.mycompany.impossiblequiz.models.QuizCharacter;

@Dao
public interface QuizCharacterDao {
    @Query("SELECT * FROM quizcharacter")
    List<QuizCharacter> getAll();

    @Insert
    void insert(QuizCharacter quizCharacter);
}
