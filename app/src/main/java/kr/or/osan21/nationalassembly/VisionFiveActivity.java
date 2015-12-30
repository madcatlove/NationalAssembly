package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class VisionFiveActivity extends AppCompatActivity {

    ImageView icon, title;
    ImageButton btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_five);

        icon = (ImageView)findViewById(R.id.five_gift_icon);
        title = (ImageView)findViewById(R.id.vision_five_title);
        btn1 = (ImageButton)findViewById(R.id.vision_five_btn1);
        btn2 = (ImageButton)findViewById(R.id.vision_five_btn2);
        btn3 = (ImageButton)findViewById(R.id.vision_five_btn3);
        btn4 = (ImageButton)findViewById(R.id.vision_five_btn4);
        btn5 = (ImageButton)findViewById(R.id.vision_five_btn5);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth()-150;
        final int height = display.getHeight();

        Glide.with(this)
                .load(R.drawable.vision_gift_icon)
                .override(100, 100)
                .fitCenter()
                .into(icon);

        Glide.with(this)
                .load(R.drawable.vision_five_title)
                .override(width, height)
                .fitCenter()
                .into(title);


        Glide.with(this)
                .load(R.drawable.vision_five_btn1)
                .override(width, height)
                .fitCenter()
                .into(btn1);

        Glide.with(this)
                .load(R.drawable.vision_five_btn2)
                .override(width, height)
                .fitCenter()
                .into(btn2);

        Glide.with(this)
                .load(R.drawable.vision_five_btn3)
                .override(width, height)
                .fitCenter()
                .into(btn3);


        Glide.with(this)
                .load(R.drawable.vision_five_btn4)
                .override(width, height)
                .fitCenter()
                .into(btn4);

        Glide.with(this)
                .load(R.drawable.vision_five_btn5)
                .override(width, height)
                .fitCenter()
                .into(btn5);

    }
}
