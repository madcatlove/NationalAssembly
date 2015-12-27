package kr.or.osan21.nationalassembly.CloudMessage;

import android.content.Intent;
import android.util.Log;

/**
 * Created by madcat on 12/27/15.
 */


// Instance ID를 획득하기 위한 리스너를 상속받음
// 토큰 갱신
public class InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService {

    private static final String LOG_TAG = "InstanceIDListenerService";

    @SuppressWarnings("LongLogTag")
    @Override
    public void onTokenRefresh() {

        Log.d(LOG_TAG, "Token refreshed!! ");

        Intent i = new Intent(this, RegistrationIntentService.class);
        startService(i);
    }
}
