package kr.or.osan21.nationalassembly.WaterSmell;

import java.util.List;

import kr.or.osan21.nationalassembly.Utils.API;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by madcat on 12/30/15.
 */
public class WaterSmellAPI extends API {

    private static final String LOG_TAG = "SupportMessageAPI";
    private WaterSmellService mWaterSmellService;

    public static interface WaterSmellService {

        @GET("/watersmell/record_list")
        void getWaterSmellList(Callback<List<WaterSmell>> cb);

        @GET("/watersmell/view/{w_id}")
        void getWaterSmell(@Path("w_id") Integer w_id, Callback<WaterSmell> cb);
    }

    public WaterSmellAPI() {
        mWaterSmellService = mRestAdapter.create(WaterSmellService.class);
    }

    /* 물향기편지 리스트 가져옴 */
    public void getWaterSmellList(Callback<List<WaterSmell>> cb) {
        mWaterSmellService.getWaterSmellList(cb);
    }

    /* 물향기편지 가져옴 */
    public void getWaterSmell(Integer w_id, Callback<WaterSmell> cb) {
        mWaterSmellService.getWaterSmell(w_id, cb);
    }
}
