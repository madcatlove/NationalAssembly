package kr.or.osan21.nationalassembly.Utils;

import android.content.Context;
import android.graphics.Typeface;

public class CustomFont {

    public static Typeface getCustomFont(Context ctx) {
        final String fontName = "SourceHanSansKR-Regular.otf";

        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), fontName);
        return tf;
    }

}
