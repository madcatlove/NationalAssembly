package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = "VisionContentActivity";

    Bitmap bitmap;
    private ImageView content;

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
            titleTxt = "오산 시민회관,\n복합문화체육센터로 재탄생!";
        else if(id == 32)
            titleTxt = "경기 남부 광역 교통의 중심,\n오산역 환승센터";
        else if(id == 33)
            titleTxt = "동탄(KTX역)~세교 전철,\n예비타당성 대상 확정";
        else if(id == 51)
            titleTxt = "독산성 복원으로\n유네스코 세계문화유산 확대지정";
        else if(id == 52)
            titleTxt = "UN초전 평화공원,\n전 세계인이 찾는 평화의 상징으로";
        else if(id == 53)
            titleTxt = "미니어처 테마파크\n대한민국의 관광명소로";
        else if(id == 54)
            titleTxt = "아이들이 멱감고\n야생화 피는 오산천";
        else if(id == 55)
            titleTxt = "오색시장, 오매장터\n전국명물로 문화관광지구 변신";

        title.setText(titleTxt);

        //이미지 적용 (본문내용+이미지)
        final ImageView img = (ImageView) findViewById(R.id.vision_content_img);
        content = (ImageView) findViewById(R.id.vision_content_text);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth();
        final int height = display.getHeight() / 3;

        Integer imgResource = null;
        if(id == 31)
            imgResource = R.drawable.vision_three_one;
        else if(id == 32)
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
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        img.setBackground(bd);
                    }
                });
        /*
        Glide.with(this)
                .load(imgResource)
                .override(width, height)
                .centerCrop()
                .into(img);
        */

        int imageId = 0;
        if(id == 31) {
            //imgResource = R.drawable.vision_three_content_01;
            //imgResource = R.drawable.t_01;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t_01);
            imageId = R.drawable.t_01;
        }
        else if(id == 32) {
            //imgResource = R.drawable.vision_three_content_02;
            //imgResource = R.drawable.t_02;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t_02);
            imageId = R.drawable.t_02;
        }
        else if(id == 33) {
            //imgResource = R.drawable.vision_three_content_03;
            //imgResource = R.drawable.t_03;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t_03);
            imageId = R.drawable.t_03;
        }
        else if(id == 51) {
            //imgResource = R.drawable.vision_five_content_01;
            //imgResource = R.drawable.f_01;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f_01);
            imageId = R.drawable.f_01;
        }
        else if(id == 52) {
            //imgResource = R.drawable.vision_five_content_02;
            //imgResource = R.drawable.f_02;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f_02);
            imageId = R.drawable.f_02;
        }
        else if(id == 53) {
            //imgResource = R.drawable.vision_five_content_03;
            //imgResource = R.drawable.f_03;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f_03);
            imageId = R.drawable.f_03;
        }
        else if(id == 54) {
            //imgResource = R.drawable.vision_five_content_04;
            //imgResource = R.drawable.f_04;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f_04);
            imageId = R.drawable.f_04;
        }
        else if(id == 55) {
            //imgResource = R.drawable.vision_five_content_05;
            //imgResource = R.drawable.f_05;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f_05);
            imageId = R.drawable.f_05;
        }

        Glide.with(this)
                .load(imageId)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        content.setImageBitmap(resource);

                    }
                });

//        content.setImageBitmap(bitmap);
//        content.setImageResource(imageId);

//        Glide.with(this)
//                .load(imgResource)
//                .fitCenter()
//                .into(content);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    public void gotoback(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {


//        Bitmap b = ((BitmapDrawable) content.getDrawable()).getBitmap();
//        content.getDrawable().setCallback(null);
//        content.setImageBitmap(null);
//        b.recycle();
//        b = null;


        super.onDestroy();
    }
}
