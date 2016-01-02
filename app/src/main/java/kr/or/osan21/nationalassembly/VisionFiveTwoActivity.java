package kr.or.osan21.nationalassembly;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionFiveTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_five_two);

        TextView title = (TextView)findViewById(R.id.vision_five_two_title);
        TextView content = (TextView)findViewById(R.id.vision_five_two_content);

        title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));
        content.setTypeface(CustomFont.getCustomFont(this, "CJKR"));

        TextView bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    public void gotoback(View v)
    {
        finish();
    }
}
