package ru.mycompany.impossiblequiz.utils;

import android.content.res.Resources;

import ru.mycompany.impossiblequiz.R;

public class Utils {
    public static String getDifficulty(int questionCount) {
        if (questionCount == 2 || questionCount == 3)
            return Resources.getSystem().getString(R.string.mode_easy);
        else if (questionCount == 4 || questionCount == 5)
            return Resources.getSystem().getString(R.string.mode_medium);
        else return Resources.getSystem().getString(R.string.mode_extreme);
    }
}
