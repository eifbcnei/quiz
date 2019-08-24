package ru.mycompany.impossiblequiz;

import ru.mycompany.impossiblequiz.models.QuestionBuilder;

public class InputValidation {
    private final static String BANNED_SYMBOL = "@";

    public static boolean hasBannedSymbol(String str) {
        return str.contains(BANNED_SYMBOL);
    }

    public static boolean isCorrectQuestion(String str) {
        return str.endsWith("?");
    }

    public static boolean isStringBlank(String str) {
        return str.trim().length() == 0;
    }

    public static boolean isQuestionValid(QuestionBuilder qb) {
        return !hasBannedSymbol(qb.answer)
                && !hasBannedSymbol(qb.question)
                && isCorrectQuestion(qb.question)
                && !isStringBlank(qb.answer)
                && !isStringBlank(qb.question);
    }
}
