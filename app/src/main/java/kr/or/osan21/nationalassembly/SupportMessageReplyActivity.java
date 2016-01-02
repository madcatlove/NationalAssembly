package kr.or.osan21.nationalassembly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageReply;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageReplyActivity extends AppCompatActivity {
    private final String LOG_TAG = "ReplyActivity";
    private List<SupportMessageReply> replies;
    private Integer reply_count;
    private ListView listview;
    private SupportMessageAPI api;
    private SupportMessageReply reply;
    private Integer num;
    private Intent i;
    private Button reply_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_message_reply);
        i = getIntent();
        num = i.getIntExtra("reply_id", -1);
        reply_btn = (Button) findViewById(R.id.reply_btn);

        final CustomAdapter adapter = new CustomAdapter();
        listview = (ListView) findViewById(R.id.message_reply_list);

        api = new SupportMessageAPI();
        api.getMessage(num, new Callback<SupportMessage>() {
            @Override
            public void success(SupportMessage supportMessage, Response response) {
                replies = new ArrayList<SupportMessageReply>();
                replies.addAll(supportMessage.getReply());

                adapter.setReplyItems(replies);
                adapter.notifyDataSetInvalidated();
                setResult(RESULT_OK);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        listview.setAdapter(adapter);
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder;
                Context context = SupportMessageReplyActivity.this;
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.reply_custom_dailog, (ViewGroup)findViewById(R.id.reply_root));
                final EditText new_username = (EditText)layout.findViewById(R.id.dialog_username);
                final EditText new_content = (EditText)layout.findViewById(R.id.dialog_content);

                builder = new AlertDialog.Builder(context);
                builder.setView(layout);
                builder.setTitle("댓글을 작성하세요.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //새로 등록된 댓글, 작성자
                        String new_content_str = new_content.getText().toString();
                        String new_username_str = new_username.getText().toString();
                        //아무것도 입력되지 않았는데 확인버튼 눌렀을 때 다이얼로그 띄우기
                        if (new_username_str.trim().length() == 0) {
                            makeAlertDialog(" 작성자를 입력하지 않았습니다. ").show();

                        } else if (new_content_str.trim().length() == 0) {
                            makeAlertDialog(" 내용을 입력하지 않았습니다. ").show();
                        }

                        Log.d(LOG_TAG, "새로운 댓글 --> " + new_username_str + " / " + new_content_str);
                        reply = new SupportMessageReply();
                        reply.setContent(new_content_str);
                        reply.setUsername(new_username_str);
                        api.writeMessageReply(num, reply, new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                setResult(RESULT_OK);
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                        api.getMessage(num, new Callback<SupportMessage>() {
                            @Override
                            public void success(SupportMessage supportMessage, Response response) {
                                replies = new ArrayList<SupportMessageReply>();
                                replies.addAll(supportMessage.getReply());
                                adapter.setReplyItems(replies);
                                adapter.notifyDataSetInvalidated();
                                setResult(RESULT_OK);
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
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

            //holder.date.setTypeface(tf);
            //holder.title.setTypeface(tf2);
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
}
