package ru.mycompany.impossiblequiz.models;

import java.util.List;

public class Question {
    private final String question;
    private final List<String> answers;

    public Question(String question, List<String> answrers) {
        this.question = question;
        this.answers = answrers;
    }

    public String getQuestion() {
        return question;
    }

    public boolean containsAnswer(String answer) {
        return answers.contains(answer);
    }
}
