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
import java.util.Objects;

import ru.mycompany.impossiblequiz.utils.Utils;

@Entity
public class QuizCharacter implements Parcelable {
    @Ignore
    private final static String QUESTION_ITEM_DIVIDER = "@";
    @Ignore
    private final static String QUESTION_DIVIDER = "!";
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @TypeConverters({QuestionConverter.class})
    private List<Question> questions;
    @TypeConverters({UriConverter.class})
    private Uri avatarUri;
    @Ignore
    private String difficultyMode;
    @Ignore
    private Status status = Status.NORMAL;
    @Ignore
    private int curQuestionIndex = 0;

    public String getDifficultyMode() {
        return difficultyMode;
    }

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
                result.append(String.format("{%s%s%s}", question.getQuestion(), QUESTION_DIVIDER, question.getAnswer()));
                result.append(QUESTION_ITEM_DIVIDER);
            }
            result.deleteCharAt(result.lastIndexOf(QUESTION_ITEM_DIVIDER));
            return result.toString();
        }

        @TypeConverter
        public List<Question> toQuestions(String data) {
            String[] str = data.split(QUESTION_ITEM_DIVIDER);
            List<Question> questions = new ArrayList<>();
            for (String s : str) {
                s = s.replace("{", "").replace("}", "");
                String[] strings = s.split(QUESTION_DIVIDER);
                questions.add(new Question(strings[0], strings[1]));
            }
            return questions;
        }
    }

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
        this.difficultyMode = Utils.getDifficulty(questions.size());
    }

    protected QuizCharacter(Parcel in) {
        name = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
        avatarUri = in.readParcelable(Uri.class.getClassLoader());
        difficultyMode = Utils.getDifficulty(questions.size());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(questions);
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
        if (curQuestionIndex == questions.size()) return null;
        return questions.get(curQuestionIndex).getQuestion();
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "QuizCharacter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questions=" + questions +
                ", avatarUri=" + avatarUri +
                ", difficultyMode='" + difficultyMode + '\'' +
                ", status=" + status +
                ", curQuestionIndex=" + curQuestionIndex +
                '}';
    }

    public void checkAnswer(String answer) {
        boolean isCorrect = questions.get(curQuestionIndex).isAnswerCorrect(answer);
        if (isCorrect) {
            curQuestionIndex++;
        } else {
            nextStatus();
            if (status == Status.NORMAL) {
                curQuestionIndex = 0;
            }
        }
    }

    public int getCurQuestionIndex() {
        return curQuestionIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizCharacter that = (QuizCharacter) o;
        return name.equals(that.name) &&
                questions.equals(that.questions) &&
                avatarUri.equals(that.avatarUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, questions, avatarUri);
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

    private void nextStatus() {
        if (status.ordinal() < Status.values().length - 1) {
            status = Status.values()[status.ordinal() + 1];
        } else {
            status = Status.values()[0];
        }
    }
}