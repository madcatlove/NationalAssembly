package kr.or.osan21.nationalassembly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageReply;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageContentActivity extends AppCompatActivity {
    private final String LOG_TAG = "MessageContentActivity";
    private SupportMessageAPI supportMessageAPI;
    private TextView title;
    private TextView username;
    private TextView content;
    private TextView regDate;
    private Button goToReply;

    private Intent intent;
    private Integer num;

    private SupportMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_message_content);
        title = (TextView) findViewById(R.id.support_message_content_title);
        username = (TextView) findViewById(R.id.support_message_content_username);
        content = (TextView) findViewById(R.id.support_message_content_content);
        regDate = (TextView) findViewById(R.id.support_message_content_regDate);
        goToReply = (Button) findViewById(R.id.support_message_content_gotoreply);
        content.setMovementMethod(new ScrollingMovementMethod());

        supportMessageAPI = new SupportMessageAPI();
        intent = getIntent();
        num = intent.getIntExtra("m_id", -1);

        //댓글달기버튼 눌렸을 때
        goToReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //댓글 액티비티 호출
                startActivity(new Intent(getBaseContext(), SupportMessageReplyActivity.class).putExtra("reply_id", num));
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
                username.setText(message.getUsername());
                content.setText(message.getContent());
                regDate.setText(message.getRegdate());
            }

            @Override
            public void failure(RetrofitError error) {
                failureDialog(" 데이터를 가져올 수 없습니다. ").show();
            }
        });
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
                    username.setText(message.getUsername());
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
}
