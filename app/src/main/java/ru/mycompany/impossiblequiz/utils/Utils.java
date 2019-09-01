package ru.mycompany.impossiblequiz.utils;

public class Utils {
    public static String getDifficulty(int questionCount) {
        if (questionCount == 2 || questionCount == 3) return "Easy";
        else if (questionCount == 4 || questionCount == 5) return "Middle";
        else return "Extreme";
    }
}
