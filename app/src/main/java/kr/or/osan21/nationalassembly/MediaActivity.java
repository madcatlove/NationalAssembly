package kr.or.osan21.nationalassembly;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.Media.Media;
import kr.or.osan21.nationalassembly.Media.MediaAPI;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MediaActivity extends AppCompatActivity {
    final String LOG_TAG = "MediaActivity";
    private RecyclerView rv;
    private CardView cv;
    private List<Media> medias;
    private RecyclerView.Adapter RVadapter;
    private Typeface tf;
    private TextView bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        tf = CustomFont.getCustomFont(this, "hans");

        init();

        bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(tf);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    private void init() {


        MediaAPI api = new MediaAPI();

        api.getMediaList(new Callback<List<Media>>() {
            @Override
            public void success(List<Media> medias, Response response) {
                Log.d(LOG_TAG, " get media list ");
                rv = (RecyclerView) findViewById(R.id.media_recycle);

                RVadapter = new CVadapter(medias);
                rv.setAdapter(RVadapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, " Error in getMediaList");
            }
        });


        //rv = (RecyclerView)findViewById(R.id.media_recycle);

        //RVadapter = new CVadapter(medias);
        //rv.setAdapter(RVadapter);
        rv = (RecyclerView)findViewById(R.id.media_recycle);

        RVadapter = new CVadapter(medias);
        rv.setAdapter(RVadapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void gotoback(View v)
    {
        finish();
    }
}
