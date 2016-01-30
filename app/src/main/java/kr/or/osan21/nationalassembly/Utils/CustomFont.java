package kr.or.osan21.nationalassembly.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class CustomFont {

    private static final String LOG_TAG = "CustomFont";

    /* 캐시 저장소에서 폰트를 가져옴 */
    public static Typeface getCustomFont(Context ctx, String name) {
        return Cache.getFont(ctx,name);
    }

    /* 폰트 생성 */
    private static Typeface createFont(Context ctx, String name) {
        String fontName = "SourceHanSansKR-Regular.otf";

        if(name.equalsIgnoreCase("CJKM"))
        {
            fontName="NotoSansCJKkr-Medium.otf";
        }
        else if(name.equalsIgnoreCase("CJKR"))
        {
            fontName="NotoSansCJKkr-Regular.otf";
        }
        else if(name.equalsIgnoreCase("CJKB"))
        {
            fontName="NotoSansCJKkr-Bold.otf";
        }
        else if(name.equalsIgnoreCase("GoB"))
        {
            fontName="RixGoB.ttf";
        }
        else if(name.equalsIgnoreCase("GoL"))
        {
            fontName="RixGoL.ttf";
        }
        else if(name.equalsIgnoreCase("GoM"))
        {
            fontName="RixGoM.ttf";
        }

        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), fontName);
        return tf;
    }

    public static void setGlobalFont(Typeface mytf, ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView)child).setTypeface(mytf);
            else if (child instanceof ViewGroup)
                setGlobalFont(mytf, (ViewGroup)child);
        }
    }


    /* 폰트 캐싱을 위함 */
    public static class Cache {
        private static HashMap<String, Typeface> data = new HashMap<String,Typeface>();

        public static Typeface getFont(Context ctx, String name) {

            if( data.containsKey(name) ) {
                Log.d(LOG_TAG, "FontCache hit!. use");
                return data.get(name);
            }

            Log.d(LOG_TAG, "FontCache miss!. create!");
            Typeface tf = createFont(ctx, name);
            data.put(name, tf);

            return tf;
        }
    }

}
