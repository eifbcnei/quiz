package ru.mycompany.impossiblequiz.models;

public class QuestionBuilder {
    private String question, answer;

    public QuestionBuilder() {
        question = "";
        answer = "";
    }

    public QuestionBuilder setQuestion(String question) {
        this.question = question;
        return this;
    }

    public QuestionBuilder setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Question build() {
        return new Question(question, answer);
    }
}