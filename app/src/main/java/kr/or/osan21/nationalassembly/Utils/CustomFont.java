package kr.or.osan21.nationalassembly.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomFont {

    public static Typeface getCustomFont(Context ctx) {
        final String fontName = "SourceHanSansKR-Regular.otf";

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

}
