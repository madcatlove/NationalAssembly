package kr.or.osan21.nationalassembly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class MediaActivity extends AppCompatActivity {
    private RecyclerView rv;
    private CardView cv;
    private ArrayList<MediaClass> medias;
    private RecyclerView.Adapter RVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        init();
    }

    private void init() {
        medias = new ArrayList<MediaClass>();
        rv = (RecyclerView)findViewById(R.id.media_recycle);
        medias.add(new MediaClass("조정훈", "꽃입니다.", R.drawable.main_person_img01));
        medias.add(new MediaClass("이석준", "선장입니다.", R.drawable.main_person_img01));
        medias.add(new MediaClass("송누리", "뽕입니다.", R.drawable.main_person_img01));
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        RVadapter = new CVadapter(medias);
        rv.setAdapter(RVadapter);
    }

}
