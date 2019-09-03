package ru.mycompany.impossiblequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity
public class Question implements Parcelable {
    private final String question;
    private final String answer;

    public Question(String question, String answers) {
        this.question = question;
        this.answer = answers;
    }


    protected Question(Parcel in) {
        question = in.readString();
        answer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswerCorrect(String answer) {
        return this.answer.equals(answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

}
