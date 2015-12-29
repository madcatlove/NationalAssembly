package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class MainActivity extends AppCompatActivity  {

    public static final String LOG_TAG = "MainActivity";
    private Typeface tf;
    private CustomFont cf;
    private ListView nav_list;
    private CustomAdapter custom_adapter;
    private LinearLayout main_menu_layout;
    private Bitmap pushOffBmp, pushOnBmp;
    private ImageView pushOnOff;
    private boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 폰트 설정 모든 뷰그룹에 적용
        tf = cf.getCustomFont(this);
        cf.setGlobalFont(tf,(ViewGroup) findViewById(R.id.drawer_layout));

        // 배경 이미지 설정
        main_menu_layout = (LinearLayout)findViewById(R.id.main_menu);
        Glide.with(this)
                .load(R.drawable.main_person_img02)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                          @Override
                          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                              Log.e(LOG_TAG, " bitmapid : " + resource);
                              BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                              main_menu_layout.setBackground(bd);
                          }
                });



        // ListView가져오기 및 custom adapter 생성
        nav_list = (ListView) findViewById(R.id.nav_list);
        custom_adapter = new CustomAdapter();
        // ListView에 어댑터 연결
        nav_list.setAdapter(custom_adapter);

        // ListView에 아이템 추가
        custom_adapter.add("푸시 알림받기");
        custom_adapter.add("건의사항 & 격려");
        custom_adapter.add("후원하기");
        custom_adapter.add("공유하기");

        // 이전 네비게이션 메뉴
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //toggle button 이미지 미리 생성
        Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.slider_push_off_btn); // 비트맵 이미지를 만든다.
        int width=160; // 가로 사이즈 지정
        int height=80; // 세로 사이즈 지정
        pushOffBmp=Bitmap.createScaledBitmap(bmp, width, height, true); // 이미지 사이즈 조정
        bmp= BitmapFactory.decodeResource(getResources(), R.drawable.slider_push_on_btn);
        pushOnBmp=Bitmap.createScaledBitmap(bmp, width, height, true);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }

    //메인 메뉴 선택 시 불림
    public void selectMenu(View v)
    {
        int selected = v.getId();
        if(selected == R.id.main_profile)
        {

        }
        else if(selected == R.id.main_activities)
        {
            startActivity(new Intent(this, WatersmellActivity.class));
        }
        else if(selected == R.id.main_vision)
        {
            startActivity(new Intent(this, VisionActivity.class));
        }
        else if(selected == R.id.main_media)
        {
        }
        else if(selected == R.id.main_notice)
        {
            // notice 
        }
    }

    public class CustomAdapter extends BaseAdapter {

        // 문자열을 보관 할 ArrayList
        private ArrayList<String> m_List;

        // 생성자
        public CustomAdapter() {
            m_List = new ArrayList<String>();
        }

        // 현재 아이템의 수를 리턴
        @Override
        public int getCount() {
            return m_List.size();
        }

        // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
        @Override
        public Object getItem(int position) {
            return m_List.get(position);
        }

        // 아이템 position의 ID 값 리턴
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 출력 될 아이템 관리
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if ( convertView == null ) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.slider_item_layout, parent, false);

                // TextView에 현재 position의 문자열 추가
                TextView text = (TextView) convertView.findViewById(R.id.slider_item_title);
                text.setText(m_List.get(position));
                text.setTypeface(tf);

                ImageView img = (ImageView)convertView.findViewById(R.id.slider_item_image);
                //pushOnOff = (ImageView)convertView.findViewById(R.id.pushOnOff);
                if(position == 0) {
                    img.setBackgroundResource(R.drawable.slider_icon_push);
                    Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.slider_push_off_btn); // 비트맵 이미지를 만든다.
                    int width=60; // 가로 사이즈 지정
                    int height=30; // 세로 사이즈 지정
                    Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, width, height, true); // 이미지 사이즈 조정
                    //pushOnOff.setBackgroundDrawable(new BitmapDrawable(getResources(), resizedbitmap));
                }
                else if(position == 1) {
                    img.setBackgroundResource(R.drawable.slider_icon_suggest);
                    //pushOnOff.setBackgroundDrawable(null);
                } else if (position == 2) {
                    img.setBackgroundResource(R.drawable.slider_icon_support);
                    //pushOnOff.setBackgroundDrawable(null);
                }
                else if(position == 3) {
                    img.setBackgroundResource(R.drawable.slider_icon_share);
                    View view = (View)convertView.findViewById(R.id.slider_item_under);
                    view.setBackgroundColor(Color.WHITE);
                    //pushOnOff.setBackgroundDrawable(null);
                }

                // 리스트 아이템을 터치 했을 때 이벤트 발생
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 터치 시 해당 아이템 이름 출력
                        Toast.makeText(context, "리스트 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return convertView;
        }

        // 외부에서 아이템 추가 요청 시 사용
        public void add(String _msg) {
            m_List.add(_msg);
        }

        // 외부에서 아이템 삭제 요청 시 사용
        public void remove(int _position) {
            m_List.remove(_position);
        }
    }

    public void changeOnOff(View v)
    {
            if(checked)
            {
                Log.d("ChangeOn","ON!");
                pushOnOff.setBackgroundResource(R.drawable.slider_push_on_btn);
                checked = false;
            }
            else
            {
                Log.d("ChangeOff", "Off");
                pushOnOff.setBackgroundResource(R.drawable.slider_push_off_btn);
                checked = true;
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


    /*
    // 이전 네비게이션 뷰 메소드
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
    }*/


}
