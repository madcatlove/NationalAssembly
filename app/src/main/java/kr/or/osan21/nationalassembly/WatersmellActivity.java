package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class WatersmellActivity extends AppCompatActivity {

    private CustomFont cf;
    private Typeface tf;
    private ListView water_smell_list;
    private CustomAdapter custom_adapter;
    private int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watersmell);

        tf = cf.getCustomFont(this);
        cf.setGlobalFont(tf, (ViewGroup) findViewById(R.id.water_smell_menu));

        // ListView가져오기 및 custom adapter 생성, 연결
        water_smell_list = (ListView)findViewById(R.id.water_smell_list);
        water_smell_list.setDivider(null);
        custom_adapter = new CustomAdapter();
        water_smell_list.setAdapter(custom_adapter);

        cnt = 0;
        // ListView에 아이템 추가
        custom_adapter.addDate("2012-10-15");
        custom_adapter.addDate("2012-12-26");
        custom_adapter.addDate("2013-01-01");
        custom_adapter.addDate("2013-01-15");
        custom_adapter.addDate("2013-06-19");
        custom_adapter.addDate("2013-07-19");
        custom_adapter.addDate("2013-10-10");
        custom_adapter.addDate("2013-12-03");

        custom_adapter.addTitle("SK 최태원 회장, 국감장 증인으로 설..");
        custom_adapter.addTitle("기쁘다 봉주 오셨네!");
        custom_adapter.addTitle("오산환승터미널 예산, 국회 통과!");
        custom_adapter.addTitle("5천만 원을 5백만 원으로!");
        custom_adapter.addTitle("강지영과 나 - 경평축구를 꿈꾸며");
        custom_adapter.addTitle("어보(御寶)를 찾아라!");
        custom_adapter.addTitle("안민석 의원의 나라 사랑법(경기신문)");
        custom_adapter.addTitle("오산 수영신화, 널리 널리 퍼져라");
    }

    public void gotoback(View v)
    {
        finish();
    }

    public class CustomAdapter extends BaseAdapter {

        // 문자열을 보관 할 ArrayList
        private ArrayList<String> date_list;
        private ArrayList<String> title_list;

        // 생성자
        public CustomAdapter() {
            date_list = new ArrayList<String>();
            title_list = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return date_list.size();
        }

        @Override
        public Object getItem(int position) {
            return date_list.get(position);
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
                convertView = inflater.inflate(R.layout.watersmell_item_layout, parent, false);

                if(cnt%2 == 0)
                    convertView.setBackgroundColor(0xffd4efff);
                else
                    convertView.setBackgroundColor(0xff5ebbef);

                cnt++;
                // TextView에 현재 position의 날짜 추가
                TextView date = (TextView) convertView.findViewById(R.id.water_smell_item_date);
                date.setText(date_list.get(position));
                date.setTypeface(tf);

                // TextView에 현재 position의 제목 추가
                TextView title = (TextView) convertView.findViewById(R.id.water_smell_item_title);
                title.setText(title_list.get(position));
                title.setTypeface(tf);

                // 리스트 아이템을 터치 했을 때 이벤트 발생
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 터치 시 해당 아이템 이름 출력
                        Toast.makeText(context, "리스트 클릭 : " + title_list.get(pos), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return convertView;
        }

        // 외부에서 아이템 추가 요청 시 사용
        public void addDate(String _msg) {
            date_list.add(_msg);
        }

        // 외부에서 아이템 삭제 요청 시 사용
        public void removeDate(int _position) {
            date_list.remove(_position);
        }

        public void addTitle(String _msg){
            title_list.add(_msg);
        }

        public void removeTitle(int _position)
        {
            title_list.remove(_position);
        }
    }
}
