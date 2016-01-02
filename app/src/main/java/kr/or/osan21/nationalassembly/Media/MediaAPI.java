package kr.or.osan21.nationalassembly.Media;

import java.util.List;

import kr.or.osan21.nationalassembly.Utils.API;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by madcat on 12/30/15.
 */
public class MediaAPI extends API {

    private static final String LOG_TAG = "MediaAPI";
    private MediaService mMediaService;

    public static interface MediaService {
        String MOBILE_VIEW_URI = "/media/mobileview/";

        @GET("/media/record_list")
        void getMediaList(Callback<List<Media>> cb);

        @GET("/media/view/{media_id}")
        void getMedia(@Path("media_id") Integer media_id, Callback<Media> cb);
    }

    public MediaAPI() {
        mMediaService = mRestAdapter.create(MediaService.class);
    }


    public void getMediaList(Callback<List<Media>> cb) {
        mMediaService.getMediaList(cb);
    }

    public void getMedia(Integer media_id, Callback<Media> cb) {
        mMediaService.getMedia(media_id, cb);
    }

}
