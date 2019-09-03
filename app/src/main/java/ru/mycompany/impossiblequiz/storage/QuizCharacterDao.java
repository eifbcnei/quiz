package ru.mycompany.impossiblequiz.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.mycompany.impossiblequiz.models.QuizCharacter;
@Dao
public interface QuizCharacterDao {

    @Query("SELECT * FROM quizcharacter")
    List<QuizCharacter> getAll();

    @Query("SELECT * FROM quizcharacter WHERE id = :id")
    QuizCharacter getById(long id);

    @Insert
    void insert(QuizCharacter quizCharacter);

    @Update
    void update(QuizCharacter quizCharacter);

    @Delete
    void delete(QuizCharacter quizCharacter);
}
