package ru.mycompany.impossiblequiz.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.AnyRes;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.mycompany.impossiblequiz.App;

public class ImageUtils {
    public static final Uri getResIdToDrawable(@AnyRes int drawableId) {
        Resources res = App.applicationContext().getResources();
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(drawableId)
                + '/' + res.getResourceTypeName(drawableId)
                + '/' + res.getResourceEntryName(drawableId));
        return imageUri;
    }


    public static Drawable getDrawableFromUri(Uri uri) {
        try {
            InputStream inputStream = App.applicationContext().getContentResolver().openInputStream(uri);
            Drawable result = Drawable.createFromStream(inputStream, uri.toString());
            return result;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
