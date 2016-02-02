package kr.or.osan21.nationalassembly;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.CloudMessage.CONST_PUSH_MESSAGE;
import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class MainActivity extends AppCompatActivity  {

    public static final String LOG_TAG = "MainActivity";
    private Typeface hans, cjkB, GoM, GoB;
    private Context context;
    private ListView nav_list;
    private CustomAdapter custom_adapter;
    private LinearLayout main_menu_layout;
    private boolean checked = false;
    private Bitmap onBmp, offBmp;
    private TextView job;
    private final String url = "http://www.osan21.or.kr/%EC%95%88%EB%AF%BC%EC%84%9D%EC%9D%84-%ED%9B%84%EC%9B%90%ED%95%B4%EC%A3%BC%EC%84%B8%EC%9A%94";
    private SharedPreferences sharedPreferences;

    //kakao share
    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    private DrawerLayout mainDrawer;
    private boolean isSlideMenuOpen = false;

    private ImageView profile_img_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // 폰트 설정 모든 뷰그룹에 적용
        hans = CustomFont.getCustomFont(this, "hans");
        cjkB = CustomFont.getCustomFont(this, "CJKB");
        GoB = CustomFont.getCustomFont(this, "GoB");
        GoM = CustomFont.getCustomFont(this, "GoM");
        CustomFont.setGlobalFont(hans, (ViewGroup) findViewById(R.id.drawer_layout));

        /*
        // 메뉴 타이틀 및 내용 글씨체 적용
        TextView profile_title = (TextView)findViewById(R.id.main_profile_title);
        profile_title.setTypeface(GoB);
        TextView activities_title = (TextView)findViewById(R.id.main_activities_title);
        activities_title.setTypeface(GoB);
        TextView media_title = (TextView)findViewById(R.id.main_media_title);
        media_title.setTypeface(GoB);
        TextView notice_title = (TextView)findViewById(R.id.main_notice_title);
        notice_title.setTypeface(GoB);
        TextView vision_title = (TextView)findViewById(R.id.main_vision_title);
        vision_title.setTypeface(GoB);

        TextView profile_content = (TextView)findViewById(R.id.main_profile_content);
        profile_content.setTypeface(GoM);
        TextView activities_content = (TextView)findViewById(R.id.main_activities_content);
        activities_content.setTypeface(GoM);
        TextView vision_content = (TextView)findViewById(R.id.main_vision_content);
        vision_content.setTypeface(GoM);
        TextView media_content = (TextView)findViewById(R.id.main_media_content);
        media_content.setTypeface(GoM);
        TextView notice_content = (TextView)findViewById(R.id.main_notice_content);
        notice_content.setTypeface(GoM);
        */

        final ImageView activities = (ImageView)findViewById(R.id.main_activities);
        final ImageView vision = (ImageView)findViewById(R.id.main_vision);
        final ImageView media = (ImageView)findViewById(R.id.main_media);
        final ImageView notice = (ImageView)findViewById(R.id.main_notice);
        final ImageView profile = (ImageView)findViewById(R.id.main_profile);

        Glide.with(this)
                .load(R.drawable.main_activities)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(LOG_TAG, " bitmapid : " + resource);
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        activities.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.main_vision)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(LOG_TAG, " bitmapid : " + resource);
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        vision.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.main_media)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(LOG_TAG, " bitmapid : " + resource);
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        media.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.main_notice)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(LOG_TAG, " bitmapid : " + resource);
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        notice.setBackground(bd);
                    }
                });

        Glide.with(this)
                .load(R.drawable.main_profile)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(LOG_TAG, " bitmapid : " + resource);
                        BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                        profile.setBackground(bd);
                    }
                });



        //오산시 국회의원 글씨체 따로 적용.
        job = (TextView)findViewById(R.id.job);
        job.setTypeface(cjkB);

        TextView profile_name = (TextView)findViewById(R.id.profile_name);
        profile_name.setTypeface(cjkB);

        // drawerlayout
        mainDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mainDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d(LOG_TAG, "onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(LOG_TAG, "DrawerOpened");
                isSlideMenuOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(LOG_TAG, "DrawerClosed");
                isSlideMenuOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d(LOG_TAG, "DrawerStateChanged");
            }
        });


        // 배경 이미지 설정
       /* main_menu_layout = (LinearLayout)findViewById(R.id.main_menu);
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
        */
        //슬라이딩메뉴 프로필 이미지 적용
        profile_img_view = (ImageView)findViewById(R.id.slider_profile_img);
        Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.slider_profile_img); // 비트맵 이미지를 만든다.
        int width=(int)(getWindowManager().getDefaultDisplay().getWidth()*0.22); // 가로 사이즈 지정
        int height=width; // 세로 사이즈 지정
        bmp = Bitmap.createScaledBitmap(bmp, width, height, true); // 이미지 사이즈 조정
        profile_img_view.setImageDrawable(new RoundedAvatarDrawable(bmp));
        profile_img_view.invalidate();


        // ListView가져오기 및 custom adapter 생성
        nav_list = (ListView) findViewById(R.id.nav_list);
        custom_adapter = new CustomAdapter();
        // ListView에 어댑터 연결
        nav_list.setAdapter(custom_adapter);

        // ListView에 아이템 추가
        custom_adapter.add("푸시 알림받기");
        custom_adapter.add("건의사항 & 격려");
        custom_adapter.add("후원하기");
        custom_adapter.add("응원메시지");
        custom_adapter.add("공유하기");

        bmp= BitmapFactory.decodeResource(getResources(), R.drawable.slider_push_on_btn); // 비트맵 이미지를 만든다.
        width=(int)(getWindowManager().getDefaultDisplay().getWidth()*0.15); // 가로 사이즈 지정
        height=(int)(width*0.7); // 세로 사이즈 지정
        onBmp=Bitmap.createScaledBitmap(bmp, width, height, true); // 이미지 사이즈 조정
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.slider_push_off_btn);
        offBmp = Bitmap.createScaledBitmap(bmp, width, height, true);

        // shared preference
        sharedPreferences = getSharedPreferences(CONST_PUSH_MESSAGE.PUSH_SHARED_PREF_STR, MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 슬라이드 메뉴 버튼 클릭시
    public void showSliding(View v)
    {
        mainDrawer.openDrawer(GravityCompat.START);
    }

    //메인 메뉴 선택 시 불림
    public void selectMenu(View v)
    {
        // slide menu가 올라와있으면.
        if( isSlideMenuOpen ) {
            return;
        }

        int selected = v.getId();
        if(selected == R.id.main_profile)
        {
            startActivity(new Intent(this, ProfileActivity.class));
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
            startActivity(new Intent(this, MediaActivity.class));
        }
        else if(selected == R.id.main_notice)
        {
            startActivity(new Intent(this, NoticeActivity.class) );
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
                text.setTypeface(hans);

                final ImageView img = (ImageView)convertView.findViewById(R.id.slider_item_image);


                // 푸쉬 이미지뷰를 다 가린다
                ImageView pushBtn = (ImageView)convertView.findViewById(R.id.pushOnOff);
                pushBtn.setImageBitmap(offBmp);
                pushBtn.setVisibility(View.INVISIBLE); //공간만 차지하도록 숨김

                if(position == 0) {
                    Glide.with(context)
                            .load(R.drawable.slider_icon_push)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Log.e(LOG_TAG, " bitmapid : " + resource);
                                    BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                                    img.setBackground(bd);
                                }
                            });
                    //img.setBackgroundResource(R.drawable.slider_icon_push);
                    pushBtn.setVisibility(View.VISIBLE);

                    /* PUSH_STATUS */
                    final boolean push_status = isPushStatusOn();
                    Log.d(LOG_TAG, " PUSH_STATUS :: " + push_status);
                    if( push_status ) {
                        pushBtn.setImageBitmap(onBmp);
                    }
                    else {
                        pushBtn.setImageBitmap(offBmp);
                    }
                }
                else if(position == 1) {
                    //img.setBackgroundResource(R.drawable.slider_icon_suggest);
                    Glide.with(context)
                            .load(R.drawable.slider_icon_suggest)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Log.e(LOG_TAG, " bitmapid : " + resource);
                                    BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                                    img.setBackground(bd);
                                }
                            });
                } else if (position == 2) {
                    //img.setBackgroundResource(R.drawable.slider_icon_support);
                    Glide.with(context)
                            .load(R.drawable.slider_icon_support)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Log.e(LOG_TAG, " bitmapid : " + resource);
                                    BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                                    img.setBackground(bd);
                                }
                            });
                } else if (position == 3) {
                    Glide.with(context)
                            .load(R.drawable.slider_icon_cheerup)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Log.e(LOG_TAG, " bitmapid : " + resource);
                                    BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                                    img.setBackground(bd);
                                }
                            });
                    //img.setBackgroundResource(R.drawable.slider_icon_cheerup);
                }
                else if(position == 4) {
                    Glide.with(context)
                            .load(R.drawable.slider_icon_share)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Log.e(LOG_TAG, " bitmapid : " + resource);
                                    BitmapDrawable bd = new BitmapDrawable(getResources(), resource);
                                    img.setBackground(bd);
                                }
                            });
                    //img.setBackgroundResource(R.drawable.slider_icon_share);
                    View view = (View)convertView.findViewById(R.id.slider_item_under);
                    view.setBackgroundColor(Color.WHITE);
                }

                // 리스트 아이템을 터치 했을 때 이벤트 발생
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 터치 시 해당 아이템 이름 출력
                        if (m_List.get(pos).equalsIgnoreCase("푸시 알림받기")) {
                            Log.d(LOG_TAG, "푸시푸시베이비");
                        } else if (m_List.get(pos).equalsIgnoreCase("건의사항 & 격려")) {
                            Log.d(LOG_TAG, "건의");
                            startActivity(new Intent(getApplicationContext(), SupportMessageActivity.class));
                        } else if (m_List.get(pos).equalsIgnoreCase("후원하기")) {
                            Log.d(LOG_TAG, "후원하기");
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } else if(m_List.get(pos).equalsIgnoreCase("응원메시지")) {
                            startActivity(new Intent(MainActivity.this, CheerupActivity.class));
                        } else if (m_List.get(pos).equalsIgnoreCase("공유하기")) {
                            Log.d(LOG_TAG, "공유하기");
                        }
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

    public void changeOnOff(View v) {

        ImageView iv = (ImageView) v;

        if (checked) {
            Log.d(LOG_TAG, "PushON");
            iv.setImageBitmap(onBmp); // 이미지뷰에 조정한 이미지 넣기
            checked = false;
            setPushStatusInShrdPreference(CONST_PUSH_MESSAGE.PUSH_ON);
        } else {
            Log.d(LOG_TAG, "PushOff");
            iv.setImageBitmap(offBmp);
            checked = true;
            setPushStatusInShrdPreference(CONST_PUSH_MESSAGE.PUSH_OFF);
        }

        v.invalidate();
    }

    public void shareKakao(View v)
    {
        try {
            kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            kakaoTalkLinkMessageBuilder.addText("더불어 민주당 국회의원 안민석 소개 어플리케이션\n");
           /* kakaoTalkLinkMessageBuilder.addAppLink("앱 바로가기",
                    new AppActionBuilder()
                            .addActionInfo(AppActionInfoBuilder
                                    .createAndroidActionInfoBuilder()
                                    .setExecuteParam("execparamkey1=1111")
                                    .setMarketParam("referrer=kakaotalklink")
                                    .build())
                            .build());*/
            kakaoTalkLinkMessageBuilder.addAppButton("앱 바로가기");
            final String msg = kakaoTalkLinkMessageBuilder.build();
            kakaoLink.sendMessage(msg, this);
        }
        catch(KakaoParameterException e)
        {
            e.printStackTrace();
        }
    }

    public void shareBand(View v)
    {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage("com.nhn.android.band");

        if(i == null) {
            // 밴드앱 설치되지 않은 경우 구글 플레이 설치페이지로 이동
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.band"));
            startActivity(intent);
            return;
        }

        try {
            String serviceDomain = "www.naver.com"; //  연동 서비스 도메인
            String encodedText = URLEncoder.encode("국회의원 안민석 앱 바로가기\nhttps://play.google.com/store/apps/details?id=kr.or.osan21.nationalassembly", "utf-8"); // 글 본문 // TODO: 마켓주소 추후에 넣기
            Uri uri = Uri.parse("bandapp://create/post?text=" + encodedText + "&route=" + serviceDomain);
            Intent newIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(newIntent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void shareSMS(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 앱 url
        String url = "https://play.google.com/store/apps/details?id=kr.or.osan21.nationalassembly";
        intent.putExtra("sms_body", "국회의원 안민석 앱 바로가기 : \n" + url);
        intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);
    }

    public void shareFacebook(View v)
    {
        String mySharedLink = "https://play.google.com/store/apps/details?id=kr.or.osan21.nationalassembly"; // TODO: 추후 마켓 주소 넣을것
        String mySubject = "국회의원 안민석 앱";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, mySubject);
        intent.putExtra(Intent.EXTRA_TEXT, mySharedLink);

        boolean fbAppFound = false;
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : resolveInfoList) {
            // 페이스북 패키지명이 com.facebook.katana
            // 페이스북 메신저 앱은 com.facebook.orca
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                fbAppFound = true;
                break;
            }
        }

        if(!fbAppFound)
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.facebook.katana"));
        }

        startActivity(intent);
    }

    public void shareTwitter(View v)
    {
        String mySharedLink = "https://play.google.com/store/apps/details?id=kr.or.osan21.nationalassembly"; // TODO: 추후 마켓 주소 넣을것
        String mySubject = "국회의원 안민석 앱 바로가기";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, mySubject);
        intent.putExtra(Intent.EXTRA_TEXT, mySubject+"\n"+mySharedLink);

        List<ResolveInfo> activityList = getPackageManager().queryIntentActivities(intent, 0);
        boolean bTwitter = false;
        for (final ResolveInfo app : activityList) {
            if (app.activityInfo.packageName.toLowerCase().startsWith("com.twitter.android")) {
                bTwitter = true;
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(name);
                startActivity(intent);
                break;
            }
        }
        if(!bTwitter){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.twitter.android"));
            startActivity(intent);
        }
    }

    /* 쉐어드프리퍼런스에 푸시 허용 상태 저장 */
    private void setPushStatusInShrdPreference(String v) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(CONST_PUSH_MESSAGE.PUSH_STATUS_KEY, v);
        ed.commit();
    }

    /* 푸시가 켜져있는 상태인지? */
    /* 쉐어드프리퍼런스 없음 ==> 켜짐상태로 간주 */
    private boolean isPushStatusOn() {
        if( sharedPreferences == null ) return true;

        final String opt = sharedPreferences.getString(CONST_PUSH_MESSAGE.PUSH_STATUS_KEY, CONST_PUSH_MESSAGE.PUSH_ON);

        return opt.equals(CONST_PUSH_MESSAGE.PUSH_ON);
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

    //둥근이미지
    public class RoundedAvatarDrawable extends Drawable {
        private final Bitmap mBitmap;
        private final Paint mPaint;
        private final RectF mRectF;
        private final int mBitmapWidth;
        private final int mBitmapHeight;

        public RoundedAvatarDrawable(Bitmap bitmap) {
            mBitmap = bitmap;
            mRectF = new RectF();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);

            this.onBoundsChange(new Rect(100,100,300,300));

            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawOval(mRectF, mPaint);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            mRectF.set(bounds);
        }

        @Override
        public void setAlpha(int alpha) {
            if (mPaint.getAlpha() != alpha) {
                mPaint.setAlpha(alpha);
                invalidateSelf();
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        public void setAntiAlias(boolean aa) {
            mPaint.setAntiAlias(aa);
            invalidateSelf();
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            mPaint.setFilterBitmap(filter);
            invalidateSelf();
        }

        @Override
        public void setDither(boolean dither) {
            mPaint.setDither(dither);
            invalidateSelf();
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }

    }
}
