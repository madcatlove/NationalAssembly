package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageActivity extends AppCompatActivity {
    private final String LOG_TAG = "SupportMessageActivity";
    private SupportMessageAPI supportMessageAPI;
    private ListView support_message_list;
    private CustomAdapter adapter;
    private Button write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_message);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        support_message_list = (ListView) findViewById(R.id.support_message_list);
        support_message_list.setDivider(null);
        adapter = new CustomAdapter();
        support_message_list.setAdapter(adapter);

        supportMessageAPI = new SupportMessageAPI();

        supportMessageAPI.getMessageList(new Callback<List<SupportMessage>>() {
            @Override
            public void success(List<SupportMessage> supportMessages, Response response) {
                Log.d(LOG_TAG, "리스트 받기");
                adapter.setMessageItems(supportMessages);
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        //건의사항 및 격려 작성하러 가기
        write = (Button) findViewById(R.id.support_message_write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SupportMessageWriteActivity.class));
            }
        });


    }

    public class CustomAdapter extends BaseAdapter {


        // 문자열을 보관 할 ArrayList
        private List<SupportMessage> messageItems;

        // 생성자
        public CustomAdapter() {
            messageItems = new ArrayList<SupportMessage>();
        }

        public void setMessageItems(List<SupportMessage> messageItems) {
            this.messageItems = messageItems;
        }

        @Override
        public int getCount() {
            return messageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return messageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 출력 될 아이템 관리
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();
            SupportViewHolder holder;

            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if (convertView == null) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.message_item_layout, parent, false);

                holder = new SupportViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.support_message_title);
                holder.username = (TextView) convertView.findViewById(R.id.support_message_username);
                holder.reply_count = (TextView) convertView.findViewById(R.id.support_message_reply);
                holder.content = (TextView) convertView.findViewById(R.id.support_message_content);
                convertView.setTag(holder);
            }
            else {
                holder = (SupportViewHolder)convertView.getTag();
            }

            holder.title.setText(messageItems.get(position).getTitle());
            holder.username.setText(messageItems.get(position).getUsername());
            holder.reply_count.setText("" + messageItems.get(position).getReply_count());
            holder.content.setText(messageItems.get(position).getContent());

            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), SupportMessageContentActivity.class).putExtra("m_id", messageItems.get(pos).getNum()));
                }
            });
            return convertView;
        }
        class SupportViewHolder {
            TextView title;
            TextView username;
            TextView content;
            TextView reply_count;
        }

    }
}

