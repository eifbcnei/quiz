package ru.mycompany.impossiblequiz.models;

import java.util.List;

public class Question {
    private final String question;
    private final List<String> answers;

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public boolean containsAnswer(String answer) {
        return answers.contains(answer);
    }
}
