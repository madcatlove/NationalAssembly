package kr.or.osan21.nationalassembly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageReply;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageReplyActivity extends AppCompatActivity {
    private final String LOG_TAG = "ReplyActivity";
    private List<SupportMessageReply> replies;
    private ListView listview;
    private SupportMessageAPI api;
    private SupportMessageReply reply;
    private Integer num;
    private Intent i;
    private Button reply_btn;
    private EditText username;
    private EditText content;
    private TextView reply_count;
    private Typeface cjkR, cjkM, cjkB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_message_reply);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        i = getIntent();
        num = i.getIntExtra("m_id", -1);

        //글씨체
        cjkM = CustomFont.getCustomFont(this, "CJKM");
        cjkR = CustomFont.getCustomFont(this, "CJKR");
        cjkB = CustomFont.getCustomFont(this, "CJKB");

        reply_btn = (Button) findViewById(R.id.reply_btn);
        reply_btn.setTypeface(cjkB);

        final CustomAdapter adapter = new CustomAdapter();

        listview = (ListView) findViewById(R.id.message_reply_list);
        username = (EditText) findViewById(R.id.reply_username);
        username.setTypeface(cjkR);
        content = (EditText) findViewById(R.id.reply_content);
        content.setTypeface(cjkR);
        reply_count = (TextView) findViewById(R.id.message_reply_count);
        reply_count.setTypeface(cjkB);
        final TextView reply_count_txt = (TextView)findViewById(R.id.message_reply_count_txt);
        reply_count_txt.setTypeface(cjkR);

        api = new SupportMessageAPI();
        api.getMessage(num, new Callback<SupportMessage>() {
            @Override
            public void success(SupportMessage supportMessage, Response response) {
                replies = new ArrayList<SupportMessageReply>();
                replies.addAll(supportMessage.getReply());
                reply_count.setText(replies.size() + "");
                reply_count_txt.setText("개의 댓글이 있습니다.");
                adapter.setReplyItems(replies);
                adapter.notifyDataSetInvalidated();
                //setResult(RESULT_OK);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        listview.setAdapter(adapter);
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_username_str = username.getText().toString().trim();
                String new_content_str = content.getText().toString().trim();
                if (new_username_str.length() == 0) {
                    //@TODO: username 비었을 때, 에러메시지 다이얼로그 부를 것.
                    username.requestFocus();
                    makeAlertDialog( "게시자를 입력하세요." ).show();
                    return;
                } else if (new_content_str.length() == 0) {
                    //@TODO: content 비었을 때, 에러메시지 다이얼로그 부를 것.
                    content.requestFocus();
                    makeAlertDialog( "댓글을 입력하세요." ).show();
                    return;
                }

                Log.d(LOG_TAG, "새로운 댓글 --> " + new_username_str + " / " + new_content_str);
                reply = new SupportMessageReply();
                reply.setContent(new_content_str);
                reply.setUsername(new_username_str);
                api.writeMessageReply(num, reply, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        api.getMessage(num, new Callback<SupportMessage>() {
                            @Override
                            public void success(SupportMessage supportMessage, Response response) {
                                replies = new ArrayList<SupportMessageReply>();
                                replies.addAll(supportMessage.getReply());
                                reply_count.setText(replies.size() + "");
                                reply_count_txt.setText("개의 댓글이 있습니다.");
                                adapter.setReplyItems(replies);
                                adapter.notifyDataSetInvalidated();
                                username.setText("");
                                content.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                                setResult(RESULT_OK);
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    private class CustomAdapter extends BaseAdapter {

        private List<SupportMessageReply> replyItems;

        public CustomAdapter() {
            replyItems = new ArrayList<SupportMessageReply>();
        }

        @Override
        public int getCount() {
            return replyItems.size();
        }

        @Override
        public Object getItem(int position) {
            return replyItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int pos = position;
            final Context context = parent.getContext();

            ReplyViewHolder holder;
            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if (convertView == null) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.reply_item_layout, parent, false);

                holder = new ReplyViewHolder();
                holder.username = (TextView) convertView.findViewById(R.id.reply_item_username);
                holder.date = (TextView) convertView.findViewById(R.id.reply_item_date);
                holder.content = (TextView) convertView.findViewById(R.id.reply_item_content);

                convertView.setTag(holder);

            } else {
                holder = (ReplyViewHolder) convertView.getTag();
            }

            holder.date.setTypeface(cjkR);
            holder.content.setTypeface(cjkR);
            holder.username.setTypeface(cjkB);
            holder.date.setText(replyItems.get(position).getRegdate());
            holder.username.setText(replyItems.get(position).getUsername());
            holder.content.setText(replyItems.get(position).getContent());

            return convertView;
        }

        public void setReplyItems(List<SupportMessageReply> items) {
            replyItems = items;
        }
    }

    class ReplyViewHolder {
        TextView username;
        TextView content;
        TextView date;
    }

    private AlertDialog makeAlertDialog(final String message) {
        AlertDialog ad = new AlertDialog.Builder( SupportMessageReplyActivity.this )
                .setMessage(message)
                .setTitle("에러가 발생하였습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return ad;
    }
    public void gotoback(View v) {
        finish();
    }

    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.top_to_bottom);
    }

}
