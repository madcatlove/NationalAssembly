package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionFiveActivity extends AppCompatActivity {

    ImageView btn1, btn2, btn3, btn4, btn5;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_five);

        title = (TextView)findViewById(R.id.vision_five_title);
        btn1 = (ImageView) findViewById(R.id.vision_five_btn1);
        btn2 = (ImageView) findViewById(R.id.vision_five_btn2);
        btn3 = (ImageView) findViewById(R.id.vision_five_btn3);
        btn4 = (ImageView) findViewById(R.id.vision_five_btn4);
        btn5 = (ImageView) findViewById(R.id.vision_five_btn5);

        title.setTypeface(CustomFont.getCustomFont(this, "GoB"));

        /* 버튼 부분 */

        Glide.with(this)
                .load(R.drawable.vision_five_btn1)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        btn1.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.vision_five_btn2)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        btn2.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.vision_five_btn3)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        btn3.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.vision_five_btn4)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        btn4.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.vision_five_btn5)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        btn5.setBackground(bd);
                    }
                });

        /*Glide.with(this)
                .load(R.drawable.vision_five_btn1)
                .centerCrop()
                .into(btn1);


        Glide.with(this)
                .load(R.drawable.vision_five_btn2)
                .centerCrop()
                .into(btn2);

        Glide.with(this)
                .load(R.drawable.vision_five_btn3)
                .centerCrop()
                .into(btn3);

        Glide.with(this)
                .load(R.drawable.vision_five_btn4)
                .centerCrop()
                .into(btn4);

        Glide.with(this)
                .load(R.drawable.vision_five_btn5)
                .centerCrop()
                .into(btn5);
                */

    }

    public void btnClicked(View v) {
        int selected = v.getId();
        if (selected == R.id.vision_five_btn1)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 51), 1);
        else if (selected == R.id.vision_five_btn2)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 52), 1);
        else if (selected == R.id.vision_five_btn3)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 53), 1);
        else if (selected == R.id.vision_five_btn4)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 54), 1);
        else if (selected == R.id.vision_five_btn5)
            startActivityForResult(new Intent(this, VisionContentActivity.class).putExtra("v_id", 55), 1);
    }
}
