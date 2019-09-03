package ru.mycompany.impossiblequiz.models;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class QuizCharacterBuilder {
    private String name;
    private List<Question> questions = new ArrayList<>();
    private Uri avatarUri = null;


    public QuizCharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public QuizCharacterBuilder setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public QuizCharacterBuilder addQuestion(Question question) {
        questions.add(question);
        return this;
    }



    public QuizCharacterBuilder setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
        return this;
    }

    public QuizCharacter createQuizCharacter() {
        return new QuizCharacter(name, questions, avatarUri);
    }
}