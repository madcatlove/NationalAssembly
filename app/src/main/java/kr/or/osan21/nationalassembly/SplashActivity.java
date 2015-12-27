package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kr.or.osan21.nationalassembly.CloudMessage.RegistrationIntentService;

public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SplashActivity";
    private static int SPLASH_TIME = 2000; // 대기시간 ms
    private Bitmap bitmap = null;
    ImageView splashImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImg = (ImageView) findViewById(R.id.splash_img);

        // 비트맵 옵션
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 6;
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_img, options);
//        splashImg.setImageBitmap(bitmap);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int width = display.getWidth();
        final int height = display.getHeight();

        Log.e(LOG_TAG, " Width : " + width + " / Height : " + height);

        Glide.with(this)
                .load(R.drawable.splash_img)
                .override(width, height)
                .fitCenter()
                .centerCrop()
                .into(splashImg);




        //적용
        splashImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

        // GCM 등록
        startGCMListen();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if(splashImg != null) {
//            splashImg.setImageBitmap(null);
//            splashImg = null;
//
//            if( bitmap != null) {
//                bitmap.recycle();
//                bitmap = null;
//            }
//        }
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
