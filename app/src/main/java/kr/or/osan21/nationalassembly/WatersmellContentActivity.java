package kr.or.osan21.nationalassembly;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmell;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmellAPI;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WatersmellContentActivity extends AppCompatActivity {

    TextView title, date, contents;
    private static final String LOG_TAG = "WaterSmellContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watersmell_content);

        // 물향기편지 id 가져옴
        Integer w_id = getIntent().getIntExtra("w_id", 1);


        title = (TextView)findViewById(R.id.ws_content_title);
        date = (TextView)findViewById(R.id.ws_content_date);
        contents = (TextView)findViewById(R.id.ws_content_contents);
        title.setTypeface(CustomFont.getCustomFont(this, "CJKM"));
        date.setTypeface(CustomFont.getCustomFont(this, "CJKR"));
        contents.setTypeface(CustomFont.getCustomFont(this, "CJKR"));

        // API 로딩
        WaterSmellAPI api = new WaterSmellAPI();
        api.getWaterSmell(w_id, new Callback<WaterSmell>() {
            @Override
            public void success(WaterSmell waterSmell, Response response) {
                Log.d(LOG_TAG, " watersmell content : " + waterSmell);

                title.setText(waterSmell.getTitle());
                date.setText(waterSmell.getRegdate());

                URLImageParser p = new URLImageParser(contents, WatersmellContentActivity.this);
                Spanned htmlContent = Html.fromHtml(waterSmell.getContent(), p, null);
                contents.setText( htmlContent );
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

}
