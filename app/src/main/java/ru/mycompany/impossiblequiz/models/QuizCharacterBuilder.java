package ru.mycompany.impossiblequiz.models;

import android.net.Uri;

import java.util.List;

public class QuizCharacterBuilder {
    private List<Question> questions;
    private QuizCharacter.Status status = QuizCharacter.Status.NORMAL;
    private Uri avatar = null;

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


    public QuizCharacterBuilder setAvatar(Uri avatar) {
        this.avatar = avatar;
        return this;
    }

    public QuizCharacter createQuizCharacter() {
        return new QuizCharacter(status, questions, 0, avatar);
    }
}