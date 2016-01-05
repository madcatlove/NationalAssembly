package kr.or.osan21.nationalassembly;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.Media.MediaAPI;
import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class MediaContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MediaContentActivity";
    private WebView media_webview;
    private ProgressDialog progressDialog;
    private TextView bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_content);

        bar_title = (TextView)findViewById(R.id.media_bar);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        Integer m_id = getIntent().getIntExtra("m_id", 1);

        media_webview = (WebView)findViewById(R.id.media_webview);
        media_webview.getSettings().setJavaScriptEnabled(true);
        media_webview.getSettings().setUseWideViewPort(true);
        media_webview.getSettings().setLoadWithOverviewMode(true);
        media_webview.setWebViewClient(new CustomWebViewClient());
        media_webview.loadUrl(API.API_URL + MediaAPI.MediaService.MOBILE_VIEW_URI + m_id);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    public void gotoback(View v)
    {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 웹뷰 리소스 소멸
        if(media_webview != null) {
            media_webview.destroy();
        }
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if( progressDialog == null) {
                progressDialog = new ProgressDialog(MediaContentActivity.this);
            }

            progressDialog.setMessage(" 페이지 로딩중 ... ");
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if( progressDialog != null) {
                if( progressDialog.isShowing() ) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        }
    }
}
