package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
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
        final int height = display.getHeight();

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
                .fitCenter()
                .into(btn2);

        Glide.with(this)
                .load(R.drawable.vision_three_btn3)
                .fitCenter()
                .into(btn3);


    }

    public void btnClicked(View v)
    {
        int selected = v.getId();
        if(selected == R.id.vision_three_btn1)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 31), 1);
        else if(selected == R.id.vision_three_btn2)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 32), 1);
        else if(selected == R.id.vision_three_btn3)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 33), 1);
    }
}

