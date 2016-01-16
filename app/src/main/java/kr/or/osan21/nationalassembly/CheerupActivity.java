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

import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmellAPI;

public class CheerupActivity extends AppCompatActivity {
    private static final String LOG_TAG = "CheerupActivity";
    private WebView cu_webview;
    private ProgressDialog progressDialog;
    private TextView bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheerup);

        bar_title = (TextView)findViewById(R.id.cheerup_text);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        cu_webview = (WebView) findViewById(R.id.cu_webview);
        cu_webview.getSettings().setJavaScriptEnabled(true);
        cu_webview.getSettings().setUseWideViewPort(true);
        cu_webview.getSettings().setLoadWithOverviewMode(true);
        cu_webview.setWebViewClient(new CustomWebViewClient());

        //@TODO: 응원메시지 URL 추가할 것.
        cu_webview.loadUrl("http://www.naver.com");
        //cu_webview.loadUrl(API.API_URL + WaterSmellAPI.WaterSmellService.MOBILE_VIEW_URI);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

    }
    public void gotoback(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 웹뷰 리소스 소멸
        if(cu_webview != null) {
            cu_webview.destroy();
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if( progressDialog == null) {
                progressDialog = new ProgressDialog(CheerupActivity.this);
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
