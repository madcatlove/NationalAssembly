package kr.or.osan21.nationalassembly;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class WatersmellContentActivity extends AppCompatActivity {

    TextView title, date, contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watersmell_content);

        title = (TextView)findViewById(R.id.ws_content_title);
        date = (TextView)findViewById(R.id.ws_content_date);
        contents = (TextView)findViewById(R.id.ws_content_contents);

        title.setTypeface(CustomFont.getCustomFont(this, "CJKM"));
        date.setTypeface(CustomFont.getCustomFont(this, "CJKR"));
        contents.setTypeface(CustomFont.getCustomFont(this, "CJKR"));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }
}
