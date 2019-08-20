package ru.mycompany.impossiblequiz.models;

public class Question {
    private final String question;
    private final String answer;

    public Question(String question, String answers) {
        this.question = question;
        this.answer = answers;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswerCorrect(String answer) {
        return this.answer.equals(answer) || this.answer.equals(translite(answer));
    }

    private String translite(String answer) {
        //TODO
        return null;
    }
}
