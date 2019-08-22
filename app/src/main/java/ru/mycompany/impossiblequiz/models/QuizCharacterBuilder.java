package ru.mycompany.impossiblequiz.models;

import java.util.List;

public class QuizCharacterBuilder {
    private List<Question> questions;
    private QuizCharacter.Status status = QuizCharacter.Status.NORMAL;
    private int curQuestionIndex = 0;

    public QuizCharacterBuilder setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public QuizCharacterBuilder setStatus(QuizCharacter.Status status) {
        this.status = status;
        return this;
    }

    public QuizCharacterBuilder setCurQuestionIndex(int curQuestionIndex) {
        this.curQuestionIndex = curQuestionIndex;
        return this;
    }

    public QuizCharacter createQuizCharacter() {
        return new QuizCharacter(status, questions, curQuestionIndex);
    }
}