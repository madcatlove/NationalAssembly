package kr.or.osan21.nationalassembly;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmellAPI;

public class CheerupActivity extends AppCompatActivity {
    private static final String LOG_TAG = "CheerupActivity";
    private TextView bar_title, cheerup_title;

    private YouTubePlayerFragment videoFragment;
    private YouTubePlayer videoPlayer = null;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheerup);

        bar_title = (TextView)findViewById(R.id.cheerup_text);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        cheerup_title = (TextView)findViewById(R.id.cheerup_title);
        cheerup_title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        playerStateChangeListener = new YoutubePlayerStateChange();
        videoFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment_cheerup);
        videoFragment.initialize(API.YOUTUBE_DEV_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(LOG_TAG, "YoutubePlayerReady!!");

                final String YOUTUBE_VIDEO_ID = "XufRb7lLRRk";

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

    }
    public void gotoback(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
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
