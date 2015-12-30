package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class VisionThreeActivity extends AppCompatActivity {

    ImageView title, icon;
    ImageButton btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_three);

        icon = (ImageView)findViewById(R.id.gift_icon);
        title = (ImageView)findViewById(R.id.vision_three_title);
        btn1 = (ImageButton)findViewById(R.id.vision_three_btn1);
        btn2 = (ImageButton)findViewById(R.id.vision_three_btn2);
        btn3 = (ImageButton)findViewById(R.id.vision_three_btn3);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth();
        int height = 100;

        Glide.with(this)
                .load(R.drawable.vision_gift_icon)
                .override(100, 100)
                .fitCenter()
                .into(icon);

        Glide.with(this)
                .load(R.drawable.vision_three_title)
                .override(width, height)
                .fitCenter()
                .into(title);


        Glide.with(this)
                .load(R.drawable.vision_three_btn1)
                .fitCenter()
                .into(btn1);

        Glide.with(this)
                .load(R.drawable.vision_three_btn2)
                .centerCrop()
                .into(btn2);

        Glide.with(this)
                .load(R.drawable.vision_three_btn3)
                .override(width, height)
                .fitCenter()
                .into(btn3);
    }
}
