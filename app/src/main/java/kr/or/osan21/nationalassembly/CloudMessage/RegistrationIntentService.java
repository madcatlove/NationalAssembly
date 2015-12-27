package kr.or.osan21.nationalassembly.CloudMessage;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import kr.or.osan21.nationalassembly.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by madcat on 12/27/15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String LOG_TAG = "RegistrationIntentService";

    public RegistrationIntentService() {
        super(LOG_TAG);
    }

    @SuppressWarnings("LongLogTag")
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(LOG_TAG, " onHandleIntent : GENERATING GCM TOKEN ");

        // GENERATING 부분 보냄
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(GCMPref.GENERATING) );


        // instance id
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;

        try {
            String defaultSenderId = getString(R.string.gcm_defaultSenderId);
            String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
            token = instanceID.getToken(defaultSenderId, scope, null);
        }
        catch(IOException e) {
            Log.e(LOG_TAG, " Error!! ");
            e.printStackTrace();
        }

        // broadcast 부분
        Intent completeRegister = new Intent(GCMPref.COMPLETE);
        completeRegister.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(completeRegister);
        Log.d(LOG_TAG, "-- GCM TOKEN :: " + token);

        updateGCMToken(token);

    }

    // GCM TOKEN 서버에 업데이트
    @SuppressWarnings("LongLogTag")
    private void updateGCMToken(final String token) {

        if( token == null) return;

        final CloudMessageAPI api = new CloudMessageAPI();

        api.updateGCMToken(token, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(LOG_TAG, " REGISTER GCM . SERVER RETURN => " + s);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, " REGISTER GCM ERROR " + error.getMessage());
            }
        });
    }
}
