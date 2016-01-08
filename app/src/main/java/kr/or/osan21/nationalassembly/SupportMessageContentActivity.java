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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageReply;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageContentActivity extends AppCompatActivity {
    private final String LOG_TAG = "MessageContentActivity";
    private SupportMessageAPI supportMessageAPI;
    private TextView title, bar;
    private TextView username;
    private TextView content;
    private TextView regDate;
    private ImageButton goToReply;

    private SupportMessageAPI api;
    private SupportMessageReply reply;

    private Typeface hans, cjkM, cjkR;

    private Intent intent;
    private Integer num;

    private SupportMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_message_content);

        bar = (TextView)findViewById(R.id.support_message_content_bar);
        title = (TextView) findViewById(R.id.support_message_content_title);
        username = (TextView) findViewById(R.id.support_message_content_username);
        content = (TextView) findViewById(R.id.support_message_content_content);
        regDate = (TextView) findViewById(R.id.support_message_content_regDate);
        goToReply = (ImageButton) findViewById(R.id.support_message_content_gotoreply);

        // 글씨체 적용
        hans = CustomFont.getCustomFont(this,"hans");
        cjkM = CustomFont.getCustomFont(this,"CJKM");
        cjkR = CustomFont.getCustomFont(this,"CJKR");

        bar.setTypeface(hans);
        title.setTypeface(cjkM);
        regDate.setTypeface(cjkR);
        username.setTypeface(cjkR);
        content.setTypeface(cjkR);

        // 내용에 스크롤 허용
        content.setMovementMethod(new ScrollingMovementMethod());

        supportMessageAPI = new SupportMessageAPI();
        intent = getIntent();
        num = intent.getIntExtra("m_id", -1);

        Glide.with(this)
                .load(R.drawable.support_message_gotoreply)
                .centerCrop()
                .into(goToReply);

        //댓글달기버튼 눌렸을 때
        goToReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //댓글 액티비티 호출
                //startActivity(new Intent(getBaseContext(), SupportMessageReplyActivity.class).putExtra("reply_id", num));
                AlertDialog dialog;
                AlertDialog.Builder builder;
                Context context = SupportMessageContentActivity.this;
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
                       /* api.writeMessageReply(num, reply, new Callback<Response>() {
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
                        });*/
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

        supportMessageAPI.getMessage(num, new Callback<SupportMessage>() {
            @Override
            public void success(SupportMessage supportMessage, Response response) {
                message = new SupportMessage();
                message.setTitle(supportMessage.getTitle());
                message.setUsername(supportMessage.getUsername());
                message.setContent(supportMessage.getContent());
                message.setRegdate(supportMessage.getRegdate());

                Log.d(LOG_TAG, " title : " + message.getTitle());
                Log.d(LOG_TAG, " username : " + message.getUsername());
                Log.d(LOG_TAG, " content : " + message.getContent());
                Log.d(LOG_TAG, " regDate : " + message.getRegdate());

                title.setText(message.getTitle());
                username.setText("게시자 : " + message.getUsername());
                content.setText(message.getContent());
                regDate.setText(message.getRegdate());
            }

            @Override
            public void failure(RetrofitError error) {
                failureDialog(" 데이터를 가져올 수 없습니다. ").show();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    //데이터 가져오기 실패했을 경우 다이얼로그
    private AlertDialog failureDialog(final String message) {
        AlertDialog ad = new AlertDialog.Builder(SupportMessageContentActivity.this)
                .setMessage(message)
                .setTitle("댓글을 달아주세요.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return ad;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            supportMessageAPI.getMessage(num, new Callback<SupportMessage>() {
                @Override
                public void success(SupportMessage supportMessage, Response response) {
                    message = new SupportMessage();
                    message.setTitle(supportMessage.getTitle());
                    message.setUsername(supportMessage.getUsername());
                    message.setContent(supportMessage.getContent());
                    message.setRegdate(supportMessage.getRegdate());

                    title.setText(message.getTitle());
                    username.setText("게시자 : " + message.getUsername());
                    content.setText(message.getContent());
                    regDate.setText(message.getRegdate());
                    setResult(RESULT_OK);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
    public void gotoback(View v) {
        finish();
    }

    private AlertDialog makeAlertDialog(final String message) {
        AlertDialog ad = new AlertDialog.Builder( SupportMessageContentActivity.this )
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
}
