package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.Notice.Notice;
import kr.or.osan21.nationalassembly.Notice.NoticeAPI;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NoticeActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NoticeActivity";
    ListView notice_list;
    CustomAdapter customAdapter;
    Typeface tf, tf2;
    private int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        //모든 text에 글꼴 적용
        tf = CustomFont.getCustomFont(this,"hans");
        tf2 = CustomFont.getCustomFont(this, "CJKM");
        CustomFont.setGlobalFont(tf, (ViewGroup) findViewById(R.id.notice_layout));

        // 리스트뷰 가져오기 및 커스텀 어답터 할당
        notice_list = (ListView)findViewById(R.id.notice_list);
        notice_list.setDivider(null);
        customAdapter = new CustomAdapter();
        notice_list.setAdapter(customAdapter);

        // 배경색 정하기 위한 count 초기화
        cnt = 0;

        // 공지사항 API 요청
        NoticeAPI api = new NoticeAPI();

        api.getNoticeList(new Callback<List<Notice>>() {
            @Override
            public void success(List<Notice> notices, Response response) {
                Log.d(LOG_TAG, " get list ");
                customAdapter.setNoticeItems(notices);
                customAdapter.notifyDataSetInvalidated();
            }

            @Override
            public void failure(RetrofitError error) {

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

    public class CustomAdapter extends BaseAdapter {

        // 문자열을 보관 할 ArrayList
        private List<Notice> noticeItems;

        // 생성자
        public CustomAdapter() {
            noticeItems = new ArrayList<Notice>();
        }

        @Override
        public int getCount() {
            return noticeItems.size();
        }

        @Override
        public Object getItem(int position) {
            return noticeItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 출력 될 아이템 관리
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if ( convertView == null ) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.notice_item_layout, parent, false);

                if(cnt%2 == 0)
                    convertView.setBackgroundColor(0xffd4efff);
                else
                    convertView.setBackgroundColor(0xff5ebbef);

                cnt++;
                // TextView에 현재 position의 날짜 추가
                TextView date = (TextView) convertView.findViewById(R.id.notice_item_date);
                date.setText(noticeItems.get(position).getRegdate());
                date.setTypeface(tf);

                // TextView에 현재 position의 제목 추가
                TextView title = (TextView) convertView.findViewById(R.id.notice_item_title);
                title.setText(noticeItems.get(position).getTitle());
                title.setTypeface(tf2);

                // 리스트 아이템을 터치 했을 때 이벤트 발생
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), NoticeContentActivity.class );
                        i.putExtra("n_id", noticeItems.get(pos).getNum() );

                        startActivity(i);
                    }
                });

            }

            return convertView;
        }

        public void setNoticeItems(List<Notice> items) {
            noticeItems = items;
        }
    }
}
