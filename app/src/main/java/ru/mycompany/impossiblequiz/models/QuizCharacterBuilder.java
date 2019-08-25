package ru.mycompany.impossiblequiz.models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class QuizCharacterBuilder {
    private String name;
    private List<Question> questions;
    private QuizCharacter.Status status = QuizCharacter.Status.NORMAL;
    private Drawable avatar = null;


    public QuizCharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public QuizCharacterBuilder setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public QuizCharacterBuilder addQuestion(Question question) {
        questions.add(question);
        return this;
    }

    public QuizCharacterBuilder setStatus(QuizCharacter.Status status) {
        this.status = status;
        return this;
    }


    public QuizCharacterBuilder setAvatar(Drawable avatar) {
        this.avatar = avatar;
        return this;
    }

    public QuizCharacter createQuizCharacter() {
        return new QuizCharacter(name, status, questions, 0, avatar);
    }
}