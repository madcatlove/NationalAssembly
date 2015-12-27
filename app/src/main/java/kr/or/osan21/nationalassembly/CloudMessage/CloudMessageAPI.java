package kr.or.osan21.nationalassembly.CloudMessage;

import android.util.Log;

import kr.or.osan21.nationalassembly.Utils.API;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by madcat on 12/27/15.
 */
public class CloudMessageAPI extends API {

    private static final String LOG_TAG = "CloudMessageAPI";
    private CloudMessageService cloudMessageService;

    public static interface CloudMessageService {

        // GCM 등록
        @FormUrlEncoded
        @POST("/gcm/register")
        void updateGCMToken(@Field("gcm_token") String gcm_token, Callback<String> cb);
    }

    public CloudMessageAPI() {
        Log.d(LOG_TAG, "--CREATED--");
        cloudMessageService = mRestAdapter.create(CloudMessageService.class);
    }

    public void updateGCMToken(String gcm_token, Callback<String> cb) {
        cloudMessageService.updateGCMToken(gcm_token, cb);
    }

}
