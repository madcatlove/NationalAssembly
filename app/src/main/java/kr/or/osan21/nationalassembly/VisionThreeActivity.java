package kr.or.osan21.nationalassembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionThreeActivity extends AppCompatActivity {

    ImageView btn1, btn2, btn3;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_three);

        //title = (ImageView)findViewById(R.id.vision_three_title);
        title = (TextView)findViewById(R.id.vision_three_title);
        btn1 = (ImageView)findViewById(R.id.vision_three_btn1);
        btn2 = (ImageView)findViewById(R.id.vision_three_btn2);
        btn3 = (ImageView)findViewById(R.id.vision_three_btn3);

        title.setTypeface(CustomFont.getCustomFont(this,"GoB"));
       /* Glide.with(this)
                .load(R.drawable.vision_three_title)
                .centerCrop()
                .into(title);
        */

        Glide.with(this)
                .load(R.drawable.vision_three_btn1)
                .centerCrop()
                .into(btn1);

        Glide.with(this)
                .load(R.drawable.vision_three_btn2)
                .centerCrop()
                .into(btn2);

        Glide.with(this)
                .load(R.drawable.vision_three_btn3)
                .centerCrop()
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

