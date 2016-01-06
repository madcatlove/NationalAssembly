package kr.or.osan21.nationalassembly;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

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

    private YouTubePlayerFragment videoFragment;
    private YouTubePlayer videoPlayer = null;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        //-------------------------------------------------------------------------
        // 비디오 설정 시작
        //-------------------------------------------------------------------------
        // 프로필 비디오 설정

        playerStateChangeListener = new YoutubePlayerStateChange();
        videoFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        videoFragment.initialize(API.YOUTUBE_DEV_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(LOG_TAG, "YoutubePlayerReady!!");

                final String YOUTUBE_VIDEO_ID = "acrpXUhVOmQ";

                // video setup
                youTubePlayer.setShowFullscreenButton(true);
                youTubePlayer.loadVideo(YOUTUBE_VIDEO_ID);
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);


                videoPlayer = youTubePlayer;
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        /*
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
        */

        //-------------------------------------------------------------------------
        // 비디오 설정 끝
        //-------------------------------------------------------------------------

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

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }


    private void setTypeface() {
        int[] titleTextView = {
                R.id.profile_barTextView,
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


    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, " call onDestroy()");
        super.onDestroy();

        if( videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }

    }



    private class YoutubePlayerStateChange implements YouTubePlayer.PlayerStateChangeListener {
        @Override
        public void onLoading() {
            Log.i(LOG_TAG, " youtube video loading... ");
        }

        @Override
        public void onLoaded(String s) {
            Log.i(LOG_TAG, " youtube video loaded... ");
        }

        @Override
        public void onAdStarted() {
            Log.i(LOG_TAG, " youtube video ad started... ");

        }

        @Override
        public void onVideoStarted() {
            Log.i(LOG_TAG, " youtube video started... ");
        }

        @Override
        public void onVideoEnded() {
            Log.i(LOG_TAG, " youtube video ended... ");
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Log.e(LOG_TAG, " Error ! ");
        }
    }
}
