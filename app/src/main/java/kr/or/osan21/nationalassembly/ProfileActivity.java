package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class ProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ProfileActivity";
    private static final String ABOUT_MOVIE_URL = API.STATIC_URL + "/movie/profile.mp4";
    private static final String PROFILE_YOUTUBE = "https://youtu.be/acrpXUhVOmQ";
    private static final String YOUTUBE_API_KEY = "AIzaSyA1PhfQKbQKnriHu4fo4vZxnQkGBPDn8sE";

    private VideoView profile_video;
    private MediaController mediaController;
    private ScrollView scv;
    private boolean videoReady = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // 프로필 비디오 설정
        profile_video = (VideoView) findViewById(R.id.profile_video);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(profile_video);

        profile_video.setMediaController(mediaController);
        profile_video.setVideoURI(Uri.parse(ABOUT_MOVIE_URL));
        profile_video.requestFocus();

        profile_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(LOG_TAG, " Video Ready ");
                videoReady = true;
            }
        });

        // 로딩이 안됬을때 클릭하면 에러
        profile_video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if( !videoReady) {
                    Toast.makeText(getBaseContext(), " 동영상 로딩중... ", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });


        // 디스플레이 사이즈 가져옴
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final int displayWidth = wm.getDefaultDisplay().getWidth();
        final int displayHeight = wm.getDefaultDisplay().getHeight();

        final int originalVideoWidth = 1920;
        final int originalVideoHeight = 1080;

        int nVideoWidth = displayWidth -  30;
        int nVideoHeight = (int) (((double)originalVideoHeight / originalVideoWidth) * nVideoWidth);

        profile_video.setLayoutParams(new LinearLayout.LayoutParams(nVideoWidth, nVideoHeight));

        // 폰트 설정
        setTypeface();

        // 배경가져옴
        scv = (ScrollView) findViewById(R.id.profile_scroll);
        Glide.with(this)
                .load(R.drawable.main_person_img03)
                .fitCenter()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        scv.setBackground(resource);
                    }
                });

    }


    private void setTypeface() {
        int[] titleTextView = {
                R.id.profile_title_academic, R.id.profile_title_past, R.id.profile_title_future
        };

        int[] contentTextView = {
                R.id.profile_content_academic, R.id.profile_content_past, R.id.profile_content_future
        };

        for(int i = 0; i < titleTextView.length; i++) {
            TextView v = (TextView) findViewById(titleTextView[i]);
            v.setTypeface(CustomFont.getCustomFont( this, "hans")); // SourceSans
        }

        for(int i = 0; i < contentTextView.length; i++) {
            TextView v = (TextView) findViewById(contentTextView[i]);
            v.setTypeface(CustomFont.getCustomFont( this, "hans") ); // SourceSans
        }

    }

    public void gotoback(View v) {
        finish();
    }

}
