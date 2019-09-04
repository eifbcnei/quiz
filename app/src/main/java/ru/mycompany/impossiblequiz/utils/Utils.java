package ru.mycompany.impossiblequiz.utils;

import android.content.res.Resources;

import ru.mycompany.impossiblequiz.App;
import ru.mycompany.impossiblequiz.R;

public class Utils {
    public static String getDifficulty(int questionCount) {
        Resources res = App.applicationContext().getResources();
        if (questionCount == 3)
            return res.getString(R.string.mode_easy);
        else if (questionCount == 4 || questionCount == 5)
            return res.getString(R.string.mode_medium);
        else return res.getString(R.string.mode_extreme);
    }
}
