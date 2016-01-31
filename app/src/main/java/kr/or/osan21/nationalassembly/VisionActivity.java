package kr.or.osan21.nationalassembly;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import kr.or.osan21.nationalassembly.Utils.CustomFont;

public class VisionActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        //모든 text에 폰트 적용
        CustomFont.setGlobalFont(CustomFont.getCustomFont(this, "hans"), (ViewGroup) findViewById(R.id.vision_layout));
        
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        setupTabHost();
        tabHost.getTabWidget().setDividerDrawable(null);
        setupTab("OsanThree");
        setupTab("OsanFive");
        tabHost.setCurrentTab(0);
    }

    private void setupTabHost()
    {
        // xml resource에서 TabHost를 받아왔다면 setup()을 수행해주어야함.
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
    }

    private void setupTab(final String tag)
    {
        View tabview = createTabView(tabHost.getContext(), tag);

        // TabSpec은 공개된 생성자가 없으므로 직접 생성할 수 없으며, TabHost의 newTabSpec메서드로 생성
        TabHost.TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview);

        if(tag.equalsIgnoreCase("OsanThree")) {
            Log.d("Enter",tag+"");
            setContent.setContent(new Intent(this, VisionThreeActivity.class));
        }
        else if(tag.equalsIgnoreCase("OsanFive")) {
            Log.d("Enter",tag+"");
            setContent.setContent(new Intent(this, VisionFiveActivity.class));
        }

        tabHost.addTab(setContent);

    }

    // Tab에 나타날 View를 구성
    private static View createTabView(final Context context, final String text)
    {
        View view = null;
        if(text.equalsIgnoreCase("OsanThree"))
        // layoutinflater를 이용해 xml 리소스를 읽어옴
            view = LayoutInflater.from(context).inflate(R.layout.vision_tab1_btn_selected, null);
        else if(text.equalsIgnoreCase("OsanFive"))
            view = LayoutInflater.from(context).inflate(R.layout.vision_tab2_btn_selected, null);

        return view;
    }

    public void gotoback(View v)
    {
        finish();
    }
}
