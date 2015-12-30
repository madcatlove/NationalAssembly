package kr.or.osan21.nationalassembly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionActivity extends AppCompatActivity {

    CustomFont cf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        //모든 text에 폰트 적용
        cf.setGlobalFont(cf.getCustomFont(this), (ViewGroup) findViewById(R.id.vision_layout));
    }

    public void gotoback(View v)
    {
        finish();
    }
}
