package kr.or.osan21.nationalassembly.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by madcat on 1/6/16.
 */
public class MediaImageView extends ImageView {

    private static final String LOG_TAG ="MediaImageView";
    private int width = 0;
    private int height = 0;
    private ViewReady viewReady;
    private boolean done = false;

    public interface ViewReady {
        public void onViewReady(View v, int width, int height);
    }

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

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(LOG_TAG, "onMeasure " + String.format("w:%d, h:%d", width, height));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v(LOG_TAG, "onDraw()");
        super.onDraw(canvas);

        if( !done) {
            if( viewReady != null) {
                viewReady.onViewReady(this, width, height);
                done = true;
            }
        }
    }

    public int getViewWidth() {
        return width;
    }


    public int getViewHeight() {
        return height;
    }

    public void setViewReady(ViewReady vr) {
        viewReady = vr;
        done = false;
    }


}
