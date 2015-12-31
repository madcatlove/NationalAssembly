package kr.or.osan21.nationalassembly;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmellAPI;

public class WatersmellContentActivity extends AppCompatActivity {


    private static final String LOG_TAG = "WaterSmellContent";
    private WebView ws_webview;
    private ProgressDialog progressDialog;
    private TextView bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watersmell_content);

        bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        // 물향기편지 id 가져옴
        Integer w_id = getIntent().getIntExtra("w_id", 1);

        ws_webview = (WebView) findViewById(R.id.ws_webview);
        ws_webview.getSettings().setJavaScriptEnabled(true);
        ws_webview.getSettings().setUseWideViewPort(true);
        ws_webview.getSettings().setLoadWithOverviewMode(true);
        ws_webview.setWebViewClient(new CustomWebViewClient());
        ws_webview.loadUrl(API.API_URL + WaterSmellAPI.WaterSmellService.MOBILE_VIEW_URI + w_id);



        // API 로딩
        WaterSmellAPI api = new WaterSmellAPI();





        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 웹뷰 리소스 소멸
        if(ws_webview != null) {
            ws_webview.destroy();
        }
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if( progressDialog == null) {
                progressDialog = new ProgressDialog(WatersmellContentActivity.this);
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
