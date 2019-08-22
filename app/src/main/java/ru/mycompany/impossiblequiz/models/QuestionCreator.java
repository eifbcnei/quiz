package ru.mycompany.impossiblequiz.models;

/**
 * this class mutable version of Question.class
 * and is used in process of creating new QuizCharacter
 * in CreateQCActivity and QuestionAdapter
 */
public class QuestionCreator {
    public String question, answer;

    public QuestionCreator() {
        question = "";
        answer = "";
    }
}