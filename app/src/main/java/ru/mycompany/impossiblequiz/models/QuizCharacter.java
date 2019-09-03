package ru.mycompany.impossiblequiz.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

@Entity
public class QuizCharacter implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @TypeConverters({QuestionConverter.class})
    private List<Question> questions;
    @TypeConverters({UriConverter.class})
    private Uri avatarUri;

    public long getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static class UriConverter {
        @TypeConverter
        public String fromUri(Uri uri) {
            return uri.toString();
        }

        @TypeConverter
        public Uri toUri(String string) {
            return Uri.parse(string);
        }
    }

    public static class QuestionConverter {
        @TypeConverter
        public String fromQuestions(List<Question> questions) {
            StringBuilder result = new StringBuilder();
            for (Question question : questions) {
                result.append(String.format("{%s!%s}", question.getQuestion(), question.getAnswer()));
                result.append("@");
            }
            result.deleteCharAt(result.lastIndexOf("@"));
            return result.toString();
        }

        @TypeConverter
        public List<Question> toQuestions(String data) {
            String[] str = data.split("@");
            List<Question> questions = new ArrayList<>();
            for (String s : str) {
                s = s.replace("{", "").replace("}", "");
                String[] strings = s.split("!");
                questions.add(new Question(strings[0], strings[1]));
            }
            return questions;
        }
    }

    @Ignore
    private Status status = Status.NORMAL;
    @Ignore
    private int curQuestionIndex = 0;

    public String getName() {
        return name;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public QuizCharacter(String name, List<Question> questions, Uri avatarUri) {
        this.name = name;
        this.questions = questions;
        this.avatarUri = avatarUri;
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