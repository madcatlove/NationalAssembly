package kr.or.osan21.nationalassembly.Utils;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by madcat on 12/28/15.
 */
public class CustomGlide implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Log.w("CustomGlide", "Setup configuration");
        builder.setDecodeFormat(DecodeFormat.ALWAYS_ARGB_8888);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Log.w("CustomGlide", "registerComponents");

    }
}
