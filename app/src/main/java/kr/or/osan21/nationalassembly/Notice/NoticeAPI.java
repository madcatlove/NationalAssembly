package kr.or.osan21.nationalassembly.Notice;

import android.util.Log;

import java.util.List;

import kr.or.osan21.nationalassembly.Utils.API;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by madcat on 12/27/15.
 */
public class NoticeAPI extends API {

    private static final String LOG_TAG = "NoticeAPI";
    private NoticeService noticeService;

    public static interface NoticeService {
        @GET("/notice/record_list")
        void getNoticeList(Callback<List<Notice>> cb);

        @GET("/notice/view/{notice_id}")
        void getNotice(@Path("notice_id") Integer notice_id, Callback<Notice> cb);
    }

    public NoticeAPI() {
        noticeService = mRestAdapter.create(NoticeService.class);
        Log.d(LOG_TAG, "--CREATED--");
    }

    // 공지사항 리스트 가져옴
    public void getNoticeList(Callback<List<Notice>> cb) {
        Log.d(LOG_TAG, "-- GET NOTICE LIST --");
        noticeService.getNoticeList(cb);
    }

    // 공지사항 특정 번호 가져옴
    public void getNotice(Integer notice_id, Callback<Notice> cb) {
        Log.d(LOG_TAG, "-- GET NOTICE --");
        noticeService.getNotice(notice_id, cb);
    }


    @Deprecated
    public void testCode() {


        // 공지사항 리스트
        getNoticeList(new Callback<List<Notice>>() {
            @Override
            public void success(List<Notice> notices, Response response) {

                for(Notice n : notices) {
                    Log.d(LOG_TAG, "" + n);
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        // 특정 공지사항(3번공지사항 가져올때)
        getNotice(3, new Callback<Notice>() {
            @Override
            public void success(Notice notice, Response response) {
                Log.d(LOG_TAG, "" + notice);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

}
