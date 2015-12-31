package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmell;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmellAPI;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WatersmellActivity extends AppCompatActivity {

    private Typeface tf, tf2;
    private ListView water_smell_list;
    private CustomAdapter custom_adapter;
    private static final String LOG_TAG = "WatersmellActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watersmell);

        tf = CustomFont.getCustomFont(this,"hans");
        tf2 = CustomFont.getCustomFont(this, "CJKM");
        CustomFont.setGlobalFont(tf, (ViewGroup) findViewById(R.id.water_smell_menu));

        // ListView가져오기 및 custom adapter 생성, 연결
        water_smell_list = (ListView)findViewById(R.id.water_smell_list);
        water_smell_list.setDivider(null);
        custom_adapter = new CustomAdapter();
        water_smell_list.setAdapter(custom_adapter);

        // 물향기편지 API요청
        WaterSmellAPI api = new WaterSmellAPI();

        // 리스트뷰에 추가=
        api.getWaterSmellList(new Callback<List<WaterSmell>>() {
            @Override
            public void success(List<WaterSmell> waterSmells, Response response) {
                Log.d( LOG_TAG, " get list " );
                custom_adapter.setWaterSmellItems(waterSmells);
                custom_adapter.notifyDataSetInvalidated();
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
        private List<WaterSmell> waterSmellItems;


        // 생성자
        public CustomAdapter() {
            waterSmellItems = new ArrayList<WaterSmell>();
        }

        @Override
        public int getCount() {
            return waterSmellItems.size();
        }

        @Override
        public Object getItem(int position) {
            return waterSmellItems.get(position);
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

            WaterSmellViewHolder holder;
            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if ( convertView == null ) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.watersmell_item_layout, parent, false);

                holder = new WaterSmellViewHolder();
                holder.date = (TextView) convertView.findViewById(R.id.water_smell_item_date);
                holder.title = (TextView) convertView.findViewById(R.id.water_smell_item_title);

                convertView.setTag(holder);
            }
            else
            {
                holder = (WaterSmellViewHolder)convertView.getTag();
            }

            if (position % 2 == 0)
                convertView.setBackgroundColor(0xffd4efff);
            else
                convertView.setBackgroundColor(0xff5ebbef);

            holder.date.setTypeface(tf);
            holder.title.setTypeface(tf2);
            holder.date.setText(waterSmellItems.get(position).getRegdate());
            holder.title.setText(waterSmellItems.get(position).getTitle());

            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Intent i = new Intent(getBaseContext(), WatersmellContentActivity.class );
                    i.putExtra("w_id", waterSmellItems.get(pos).getNum() );

                    startActivity(i);
                }
            });

            return convertView;
        }

        public void setWaterSmellItems(List<WaterSmell> items) {
            waterSmellItems = items;
        }
    }

    class WaterSmellViewHolder {
        TextView title;
        TextView date;
    }
}
