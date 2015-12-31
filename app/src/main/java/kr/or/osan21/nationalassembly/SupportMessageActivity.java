package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import kr.or.osan21.nationalassembly.WaterSmell.WaterSmell;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageActivity extends AppCompatActivity {
    private final String LOG_TAG = "SupportMessageActivity";
    private SupportMessageAPI supportMessageAPI;
    private ListView support_message_list;
    private RecyclerView rv;
    private ArrayList<SupportMessageClass> messages;
    private RecyclerView.Adapter RVadapter;
    private Button write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_message);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        supportMessageAPI = new SupportMessageAPI();
        // 리스트뷰에 추가
        supportMessageAPI.getMessageList(new Callback<List<SupportMessage>>() {
            @Override
            public void success(List<SupportMessage> supportMessages, Response response) {
                //@TODO:: custom adapter만들어서 set하기
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        //건의사항 및 격려 작성하러 가기
        write = (Button)findViewById(R.id.support_message_write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SupportMessageWriteActivity.class));
            }
        });


    }


    private void init() {
        messages = new ArrayList<SupportMessageClass>();
        rv = (RecyclerView)findViewById(R.id.support_message_recycle);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        SupportmessageAdapter adapter = new SupportmessageAdapter();
        //RVadapter = new CVadapter();
        //rv.setAdapter(adapter);

        for(int i = 0; i < 10; i++) {
            messages.add(new SupportMessageClass(i, "Title " + i, "Content " + i, "Username " + i,  "Regdate " + i));
        }
    }

    private class SupportmessageAdapter extends BaseAdapter implements View.OnClickListener {
        private ArrayList<SupportMessageClass> messages;
        private SupportmessageAdapter() {
            messages = new ArrayList<SupportMessageClass>();
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if (convertView == null) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.support_message_card, parent, false);

                // TextView에 현재 position의 문자열 추가
                TextView title = (TextView) convertView.findViewById(R.id.support_message_title);
                TextView content = (TextView)convertView.findViewById(R.id.support_message_content);
                TextView username = (TextView)convertView.findViewById(R.id.support_message_username);
                ImageView replyIcon = (ImageView)convertView.findViewById(R.id.support_message_replyIcon);
                TextView reply = (TextView)convertView.findViewById(R.id.support_message_reply);

            }
            return convertView;
        }
        @Override
        public void onClick(View v) {

        }
    }

    /*
    * private Integer num;
        private String title;
        private String content;
        private String username;
        private String regdate;
    */
    private class SupportMessageClass {
        private Integer num;
        private String title;
        private String content;
        private String username;

        public SupportMessageClass() {}
        public SupportMessageClass(Integer num, String title, String content, String username, String regdate) {
            this.num = num;
            this.title = title;
            this.content = content;
            this.username = username;
            this.regdate = regdate;
        }

        private String regdate;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }
    }
}

