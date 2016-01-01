package kr.or.osan21.nationalassembly;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.or.osan21.nationalassembly.SupportMessage.SupportMessage;
import kr.or.osan21.nationalassembly.SupportMessage.SupportMessageAPI;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SupportMessageWriteActivity extends AppCompatActivity {
    private static String LOG_TAG = "SupportMessageWriteActivity";
    private TextView title;
    private TextView username;
    private TextView content;
    private Button submit;
    private SupportMessageAPI supportMessageAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportmessagewrite);
        title = (TextView)findViewById(R.id.support_message_write_title);
        username = (TextView)findViewById(R.id.support_message_write_username);
        content = (TextView)findViewById(R.id.support_message_write_content);
        submit = (Button)findViewById(R.id.support_message_write_submit);

        // api call
        supportMessageAPI = new SupportMessageAPI();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //날짜가져오기
                final Date today = new Date();
                final SimpleDateFormat dateFormat;
                dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
                Log.d(LOG_TAG, "작성날짜: " + dateFormat.format(today));

                final String str_title = title.getText().toString().trim();
                final String str_content = content.getText().toString().trim();
                final String str_username = username.getText().toString().trim();
                final String str_regdate = dateFormat.format(today);

                //작성버튼 눌렀는데 사용자 비었을 때 처리
                if( str_username.length() == 0) {
                    makeAlertDialog(" 작성자를 입력해주세요. ").show();
                    username.requestFocus();
                    return;
                }

                //작성버튼 눌렀는데 제목 비었을 때 처리
                else if( str_title.length() == 0) {
                    makeAlertDialog(" 제목을 입력해주세요. ").show();
                    title.requestFocus();
                    return;
                }

                //내용이 비었을 때 처리
                else if( str_content.length() == 0) {
                    makeAlertDialog(" 내용을 입력해주세요. ").show();
                    content.requestFocus();
                    return;
                }

                // 서버에 격려메세지 전송
                SupportMessage message = new SupportMessage();
                message.setTitle( str_title );
                message.setContent( str_content );
                message.setUsername( str_username );
                message.setRegdate( str_regdate );

                supportMessageAPI.writeMessage(message, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        final String result = response.getBody().toString();
                        Log.d(LOG_TAG, "result ==> " + result);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(LOG_TAG, "writeSupportMessageFail :: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "글 작성 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private AlertDialog makeAlertDialog(final String message) {
        AlertDialog ad = new AlertDialog.Builder( SupportMessageWriteActivity.this )
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
