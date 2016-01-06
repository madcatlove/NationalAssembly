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

public class VisionContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_content);

        TextView title = (TextView) findViewById(R.id.vision_content_title);
        title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));

        TextView bar_title = (TextView) findViewById(R.id.vision_content_bar);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        Integer id = getIntent().getIntExtra("v_id",1);
        //타이틀 적용
        String titleTxt="";
        if(id == 31)
            titleTxt = "오산 시민회관 재건축,\n복합문화체육센터로";
        else if(id == 32)
            titleTxt = "경기 남부 광역 교통역 중심,\n오산역 환승센터";
        else if(id == 33)
            titleTxt = "동탄(KTX역) ~ 세교\n복선 전철 예비타당성 확정";
        else if(id == 51)
            titleTxt = "독산성 복원으로 유네스코\n세계문화유산 확대";
        else if(id == 52)
            titleTxt = "UN포전 평화공원,\n전 세계인이 찾는\n평화의 상징으로";
        else if(id == 53)
            titleTxt = "미니어처 테마파크\n아시아 최대 소인국 테마파크";
        else if(id == 54)
            titleTxt = "아이들이 멱감고,\n야생화 피는 오산천";
        else if(id == 55)
            titleTxt = "오색시장, 오매장터\n문화광광지구로 변신";

        title.setText(titleTxt);

        //이미지 적용 (본문내용+이미지)
        ImageView img = (ImageView) findViewById(R.id.vision_content_img);
        ImageView content = (ImageView) findViewById(R.id.vision_content_text);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth();
        final int height = display.getHeight() / 3;

        Integer imgResource = R.drawable.vision_three_one;

        if(id == 32)
            imgResource = R.drawable.vision_three_two;
        else if(id == 33)
            imgResource = R.drawable.vision_three_three;
        else if(id == 51)
            imgResource = R.drawable.vision_five_one;
        else if(id == 52)
            imgResource = R.drawable.vision_five_two;
        else if(id == 53)
            imgResource = R.drawable.vision_five_three;
        else if(id == 54)
            imgResource = R.drawable.vision_five_four;
        else if(id == 55)
            imgResource = R.drawable.vision_five_five;

        Glide.with(this)
                .load(imgResource)
                .override(width, height)
                .centerCrop()
                .into(img);

        imgResource = R.drawable.vision_three_content_01;

        if(id == 32)
            imgResource = R.drawable.vision_three_content_02;
        else if(id == 33)
            imgResource = R.drawable.vision_three_content_03;
        else if(id == 51)
            imgResource = R.drawable.vision_five_content_01;
        else if(id == 52)
            imgResource = R.drawable.vision_five_content_02;
        else if(id == 53)
            imgResource = R.drawable.vision_five_content_03;
        else if(id == 54)
            imgResource = R.drawable.vision_five_content_04;
        else if(id == 55)
            imgResource = R.drawable.vision_five_content_05;

        Glide.with(this)
                .load(imgResource)
                .fitCenter()
                .into(content);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    public void gotoback(View v) {
        finish();
    }
}
