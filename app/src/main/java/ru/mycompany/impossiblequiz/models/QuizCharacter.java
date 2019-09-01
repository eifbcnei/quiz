package ru.mycompany.impossiblequiz.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuizCharacter implements Parcelable {
    private String name;
    private Status status;
    private List<Question> questions;
    private int curQuestionIndex;
    private Uri avatarUri;

    public String getName() {
        return name;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public QuizCharacter(String name, Status status, List<Question> questions, Uri avatar) {
        this.status = status;
        this.name = name;
        this.questions = questions;
        this.curQuestionIndex = 0;
        this.avatarUri = avatar;
    }

    protected QuizCharacter(Parcel in) {
        name = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
        status = Status.valueOf(in.readString());
        avatarUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(questions);
        dest.writeString(status.name());
        dest.writeParcelable(avatarUri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizCharacter> CREATOR = new Creator<QuizCharacter>() {
        @Override
        public QuizCharacter createFromParcel(Parcel in) {
            return new QuizCharacter(in);
        }

        @Override
        public QuizCharacter[] newArray(int size) {
            return new QuizCharacter[size];
        }
    };

    public Uri getAvatarUri() {
        return avatarUri;
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
                "name='" + name + '\'' +
                ", status=" + status +
                ", questions=" + questions +
                ", curQuestionIndex=" + curQuestionIndex +
                ", avatarUri=" + avatarUri +
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