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

public class VisionFiveFourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_five_four);

        TextView title = (TextView)findViewById(R.id.vision_five_four_title);
        title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));

        TextView bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        //이미지 적용 (본문내용+이미지)
        ImageView img = (ImageView)findViewById(R.id.vision_five_four_img);
        ImageView content = (ImageView)findViewById(R.id.vision_five_four_content);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth();
        final int height = display.getHeight()/3;

        Glide.with(this)
                .load(R.drawable.vision_five_four)
                .override(width, height)
                .centerCrop()
                .into(img);

        Glide.with(this)
                .load(R.drawable.vision_five_content_04)
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
