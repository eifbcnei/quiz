package ru.mycompany.impossiblequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuizCharacter implements Parcelable {
    private Status status;
    private List<Question> questions;
    private int curQuestionIndex;

    public Status getStatus() {
        return status;
    }

    public QuizCharacter(){
        status=Status.NORMAL;
        curQuestionIndex=0;
        questions=null;
    }

    public QuizCharacter(Status status, List<Question> questions) {
        this.status = status;
        this.questions = questions;
        curQuestionIndex = 0;
    }

    public QuizCharacter(Status status, List<Question> questions, int curQuestionIndex) {
        this.status = status;
        this.questions = questions;
        this.curQuestionIndex = curQuestionIndex;
    }

    public static final Creator<QuizCharacter> CREATOR = new Creator<QuizCharacter>() {
        @Override
        public QuizCharacter createFromParcel(Parcel in) {
            Status status = Status.valueOf(in.readString());
            List<Question> questions = in.readArrayList(Question.class.getClassLoader());
            int curIndex = in.readInt();
            return new QuizCharacter(status, questions, curIndex);
        }

        @Override
        public QuizCharacter[] newArray(int size) {
            return new QuizCharacter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status.name());
        dest.writeValue(questions);
        dest.writeInt(curQuestionIndex);
    }

    public boolean checkAnswer(String answer) {
        boolean isCorrect = questions.get(curQuestionIndex).containsAnswer(answer);
        return isCorrect;
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
        if(status.ordinal()<Status.values().length-1){
            status=Status.values()[status.ordinal()+1];
        }else{
            status=Status.values()[0];
        }
    }
}