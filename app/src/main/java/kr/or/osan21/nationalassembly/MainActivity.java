package kr.or.osan21.nationalassembly;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.CloudMessage.RegistrationIntentService;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import retrofit.RestAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = "MainActivity";
    private Typeface mTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 폰트 설정 모든 뷰그룹에 적용
        mTypeface = Typeface.createFromAsset(getAssets(), "SourceHanSansKR-Regular.otf");
        ViewGroup root = (ViewGroup) findViewById(R.id.main_menu);
        setGlobalFont(root);

        // 네비게이션 메뉴
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // GCM 시작
        startGCMListen();
    }



    void setGlobalFont(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView)child).setTypeface(mTypeface);
            else if (child instanceof ViewGroup)
                setGlobalFont((ViewGroup)child);
        }
    }

    public void selectMenu(View v)
    {
        int selected = v.getId();
        if(selected == R.id.main_profile)
        {

        }
        else if(selected == R.id.main_activities)
        {

        }
        else if(selected == R.id.main_vision)
        {

        }
        else if(selected == R.id.main_media)
        {
        }
        else if(selected == R.id.main_notice)
        {
            // notice 
        }
    }

    public void call_national_number(View v) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-784-3877")));
    }

    public void call_local_number(View v) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:031-378-2273")));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.push) {
            // Handle the camera action
        } else if (id == R.id.suggest) {

        } else if (id == R.id.support) {

        } else if (id == R.id.share) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void startGCMListen() {
        Log.e(LOG_TAG, " START_GCM_LISTEN ");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
