package kr.or.osan21.nationalassembly.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by madcat on 1/6/16.
 */
public class MediaImageView extends ImageView {

    private static final String LOG_TAG ="MediaImageView";
    private int width = 0;
    private int height = 0;

    public MediaImageView(Context context) {
        super(context);
    }

    public MediaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(LOG_TAG, "onMeasure");

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

    }


    public int getViewWidth() {
        return width;
    }


    public int getViewHeight() {
        return height;
    }


}
