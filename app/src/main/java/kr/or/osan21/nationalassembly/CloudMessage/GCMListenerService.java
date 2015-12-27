package kr.or.osan21.nationalassembly.CloudMessage;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by madcat on 12/27/15.
 */
public class GCMListenerService extends GcmListenerService {

    private static final String LOG_TAG = "GCMListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(LOG_TAG, " 메세지 수신 ");
    }
}
