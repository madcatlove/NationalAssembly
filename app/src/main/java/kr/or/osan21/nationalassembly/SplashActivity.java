package kr.or.osan21.nationalassembly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import kr.or.osan21.nationalassembly.CloudMessage.RegistrationIntentService;

public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SplashActivity";
    private static int SPLASH_TIME = 2000; // 대기시간 ms
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashImg = (ImageView) findViewById(R.id.splash_img);

        // 비트맵 옵션
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_img, options);

        // 적용
        splashImg.setImageBitmap(bitmap);
        splashImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                bitmap = null;
                finish();
            }
        });

        // GCM 등록
        startGCMListen();

        // 3초 대기
//        Handler hd = new Handler();
//        hd.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getBaseContext(), MainActivity.class));
//
//                // release resource
//                bitmap = null;
//
//                finish();
//            }
//        }, 3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // GCM 시작
    private void startGCMListen() {
        Log.e(LOG_TAG, " START_GCM_LISTEN ");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
