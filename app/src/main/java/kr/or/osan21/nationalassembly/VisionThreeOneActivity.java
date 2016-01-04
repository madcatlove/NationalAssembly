package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionThreeOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_three_one);

        // 상단바와 글 제목 글씨체 적용
        TextView title = (TextView)findViewById(R.id.vision_three_one_title);
        title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));

        TextView bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        //이미지 적용 (본문내용+이미지)
        ImageView img = (ImageView)findViewById(R.id.vision_three_one_img);
        ImageView content = (ImageView)findViewById(R.id.vision_three_one_content);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth();
        final int height = display.getHeight()/3;

        Glide.with(this)
                .load(R.drawable.vision_three_one)
                .override(width, height)
                .centerCrop()
                .into(img);

        Glide.with(this)
                .load(R.drawable.vision_three_content_01)
                .fitCenter()
                .into(content);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    public void gotoback(View v)
    {
        finish();
    }
}
