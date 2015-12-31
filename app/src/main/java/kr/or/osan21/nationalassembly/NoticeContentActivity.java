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

import org.w3c.dom.Text;

import kr.or.osan21.nationalassembly.Notice.Notice;
import kr.or.osan21.nationalassembly.Notice.NoticeAPI;
import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmellAPI;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NoticeContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NoticeContentActivity";
    private TextView bar_title, sub_title;
    private TextView content_title, content_date, content_contents, content_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);

        bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(CustomFont.getCustomFont(this, "hans"));

        sub_title = (TextView)findViewById(R.id.sub_title);
        sub_title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));

        content_title = (TextView)findViewById(R.id.notice_content_title);
        content_title.setTypeface(CustomFont.getCustomFont(this, "CJKB"));

       // content_date = (TextView)findViewById(R.id.notice_content_date);
       // content_date.setTypeface(CustomFont.getCustomFont(this, "CJKM"));

        content_username = (TextView)findViewById(R.id.notice_content_username);
        content_username.setTypeface(CustomFont.getCustomFont(this, "CJKM"));

        content_contents = (TextView)findViewById(R.id.notice_content_contents);
        content_contents.setTypeface(CustomFont.getCustomFont(this, "CJKR"));

        // 공지사항 id 가져옴
        Integer n_id = getIntent().getIntExtra("n_id", 1);

        // 뷰에 가져온 공지사항 내용 적용
        NoticeAPI api = new NoticeAPI();
        api.getNotice(n_id, new Callback<Notice>() {
            @Override
            public void success(Notice notice, Response response) {
                content_title.setText(notice.getTitle());
                //content_date.setText("작성일 : " + notice.getRegdate());
                content_username.setText(notice.getName() + " / " + notice.getRegdate());
                content_contents.setText(notice.getContent());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "Get notice failure");
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    public void gotoback(View v)
    {
        finish();
    }

}
