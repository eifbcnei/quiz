package ru.mycompany.impossiblequiz.viewsImpls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import ru.mycompany.impossiblequiz.R;

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {
    private Context mContext;
    private AttributeSet mAttrs;
    private int mDefStyleAttrs;

    public MyImageView(Context context) {
        super(context, null, 0);
        mContext = context;
        init(null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        mAttrs = attrs;
        init(attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttrs = attrs;
        mDefStyleAttrs = defStyleAttr;
        init(attrs);
    }

    private final static float DEFAULT_ASPECT_RATIO = 1.78f;

    private float ratio = DEFAULT_ASPECT_RATIO;

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.MyImageView);
            ratio = typedArray.getFloat(R.styleable.MyImageView_aspectRatio, DEFAULT_ASPECT_RATIO);
            typedArray.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newHeight = (int) (getMeasuredWidth() / ratio);
        setMeasuredDimension(getMeasuredWidth(), newHeight);
    }
}
