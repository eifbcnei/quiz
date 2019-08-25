package ru.mycompany.impossiblequiz.models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class QuizCharacter {
    private String name;
    private Status status;
    private List<Question> questions;
    private int curQuestionIndex;
    private Drawable avatar;

    public QuizCharacter(String name, Status status, List<Question> questions, int curQuestionIndex, Drawable avatar) {
        this.status = status;
        this.name = name;
        this.questions = questions;
        this.curQuestionIndex = 0;
        this.avatar = avatar;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getCurrentQuestion() {
        return questions.get(curQuestionIndex).getQuestion();
    }

    public Status getStatus() {
        return status;
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