package ru.mycompany.impossiblequiz.models;

import java.util.List;

public class QuizCharacter {
    private Status status;
    private List<Question> questions;
    private int curQuestionIndex;

    public List<Question> getQuestions() {
        return questions;
    }

    public String getCurrentQuestion() {
        return questions.get(curQuestionIndex).getQuestion();
    }

    public Status getStatus() {
        return status;
    }

    public QuizCharacter(List<Question> questions) {
        this.status = Status.NORMAL;
        this.questions = questions;
        curQuestionIndex = 0;
    }

    public QuizCharacter(Status status, List<Question> questions, int curQuestionIndex) {
        this.status = status;
        this.questions = questions;
        this.curQuestionIndex = curQuestionIndex;
    }

    @Override
    public String toString() {
        return "QuizCharacter{" +
                "status=" + status +
                ", questions=" + questions +
                ", curQuestionIndex=" + curQuestionIndex +
                '}';
    }

    public void checkAnswer(String answer) {
        boolean isCorrect = questions.get(curQuestionIndex).isAnswerCorrect(answer);
        if (isCorrect) {
            if (curQuestionIndex != questions.size() - 1) {
                curQuestionIndex++;
            }
        } else {
            nextStatus();
            if (status == Status.NORMAL) {
                curQuestionIndex = 0;
            }
        }
    }

    public enum Status {
        NORMAL(255, 255, 255),
        WARNING(255, 120, 0),
        DANGER(255, 60, 60),
        CRITICAL(255, 0, 0);

        public final int red;
        public final int green;
        public final int blue;

        Status(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    public void nextStatus() {
        if (status.ordinal() < Status.values().length - 1) {
            status = Status.values()[status.ordinal() + 1];
        } else {
            status = Status.values()[0];
        }
    }
}