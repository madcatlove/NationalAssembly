package kr.or.osan21.nationalassembly;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        //모든 text에 폰트 적용
        CustomFont.setGlobalFont(CustomFont.getCustomFont(this, "hans"), (ViewGroup) findViewById(R.id.vision_layout));
        
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        
    }

    public void gotoback(View v)
    {
        finish();
    }
}
