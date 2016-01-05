package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
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

import kr.or.osan21.nationalassembly.Media.Media;
import kr.or.osan21.nationalassembly.Media.MediaAPI;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MediaActivity extends AppCompatActivity {
    final String LOG_TAG = "MediaActivity";
    private ListView media_list;
    private CustomAdapter adapter;
    private MediaAPI api;
    private Typeface tf, tf2;
    private TextView bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        tf = CustomFont.getCustomFont(this, "hans");
        tf2 = CustomFont.getCustomFont(this, "CJKB");

        //init();

        bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(tf);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        media_list = (ListView)findViewById(R.id.media_list);
        media_list.setDivider(null);
        adapter = new CustomAdapter();
        media_list.setAdapter(adapter);

        //api호출
        api = new MediaAPI();
        api.getMediaList(new Callback<List<Media>>() {
            @Override
            public void success(List<Media> medias, Response response) {

                for(Media m : medias) {
                    Log.d(LOG_TAG, "" + m);
                }

                adapter.setMedias(medias);
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void gotoback(View v) {
        finish();
    }

    private class CustomAdapter extends BaseAdapter {
        private List<Media> medias;

        public CustomAdapter() {
            medias = new ArrayList<Media>();
        }

        public void setMedias(List<Media> medias) {
            this.medias = medias;
        }

        public List<Media> getMedias() {
            return medias;
        }

        @Override
        public int getCount() {
            return medias.size();
        }

        @Override
        public Object getItem(int position) {
            return medias.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();
            MediaViewHolder holder;

            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if (convertView == null) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.media_item_layout, parent, false);

                holder = new MediaViewHolder();

                holder.title = (TextView) convertView.findViewById(R.id.media_title);
                holder.content = (TextView) convertView.findViewById(R.id.media_content);

                convertView.setTag(holder);
            }
            else {
                holder = (MediaViewHolder)convertView.getTag();
            }

            holder.title.setTypeface(tf2);
            holder.content.setTypeface(tf);
            holder.title.setText(medias.get(position).getTitle());
            holder.content.setText(medias.get(position).getContent());
            //리스트에서 타이틀 보여줄 때 15자리로 제한.
            /*
            String str_temp = "";
            if(messageItems.get(position).getTitle().length() > 12) {
                str_temp = messageItems.get(position).getTitle();
                str_temp = str_temp.substring(0, 13);
                str_temp += "..";
                holder.title.setText(str_temp);
            } else {
                holder.title.setText(messageItems.get(position).getTitle());
            }
            if(messageItems.get(position).getUsername().length() > 12) {
                str_temp = messageItems.get(position).getUsername();
                str_temp = str_temp.substring(0, 13);
                str_temp += "..";
                Log.d(LOG_TAG, "User name : " + str_temp);
                holder.username.setText(str_temp);
            } else {
                holder.username.setText(messageItems.get(position).getUsername());
            }
            holder.reply_count.setText("" + messageItems.get(position).getReply_count());
            holder.content.setText(messageItems.get(position).getContent());
            */
            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(), medias.get(position).getTitle().toString(), Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getBaseContext(), MediaContentActivity.class).putExtra("m_id", medias.get(pos).getNum()), 1);
                }
            });
            return convertView;
        }
    }

    class MediaViewHolder {
        TextView title;
        TextView content;
    }
}
