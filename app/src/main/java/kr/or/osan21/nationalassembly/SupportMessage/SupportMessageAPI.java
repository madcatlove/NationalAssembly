package kr.or.osan21.nationalassembly.SupportMessage;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.osan21.nationalassembly.Utils.API;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by madcat on 12/27/15.
 */
public class SupportMessageAPI extends API {

    private static final String LOG_TAG = "SupportMessageAPI";
    private SupportMessageService mSupportMessageService;

    public static interface SupportMessageService {

        /* 격려메세지 리스트 */
        @GET("/message/record_list")
        void getMessageList(Callback<List<SupportMessage>> cb);

        /* 격려메세지 번호 가져옴 */
        @GET("/message/view/{message_id}")
        void getMessage(@Path("message_id") Integer message_id, Callback<SupportMessage> cb);

        /* 격려메세지 작성 */
        @FormUrlEncoded
        @POST("/message/writeMessage")
        void writeMessage( @FieldMap Map<String, String> fieldMap, Callback<Response> cb);

        /* 격려메세지 댓글 작성 */
        @FormUrlEncoded
        @POST("/message/writeMessageReply/{message_id}")
        void writeMessageReply(@Path("message_id") Integer message_id,  @FieldMap Map<String, String> fieldMap, Callback<Response> cb);
    }

    public SupportMessageAPI() {
        mSupportMessageService = mRestAdapter.create(SupportMessageService.class);
        Log.d(LOG_TAG, "--CREATED--");
    }

    // 격려 메세지 리스트 가져옴
    public void getMessageList(Callback<List<SupportMessage>> cb) {
        mSupportMessageService.getMessageList(cb);
    }

    // 격려 메세지 자세히
    public void getMessage(Integer message_id, Callback<SupportMessage> cb) {
        mSupportMessageService.getMessage(message_id, cb);
    }

    public void writeMessage(SupportMessage supportMessage, Callback<Response> cb) {

        Map<String, String> m = new HashMap<String, String>();
        m.put("title", supportMessage.getTitle());
        m.put("content", supportMessage.getContent());
        m.put("username", supportMessage.getUsername());

        mSupportMessageService.writeMessage(m, cb);
    }

    public void writeMessageReply(Integer message_id, SupportMessageReply supportMessageReply, Callback<Response> cb) {

        Map<String, String> m = new HashMap<String, String>();
        m.put("content", supportMessageReply.getContent());
        m.put("username", supportMessageReply.getUsername());


        mSupportMessageService.writeMessageReply(message_id, m, cb);
    }

    @Deprecated
    public void testCode() {


        /*
        // 격려메세지 전송
        SupportMessage sm = new SupportMessage();
        sm.setTitle(" 테스트 제목1 ");
        sm.setContent(" 테스트 내용 1 ");
        sm.setUsername("테스트유저");

        writeMessage(sm, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.d(LOG_TAG, "writeMessageTEST");
                Log.d(LOG_TAG, new String(((TypedByteArray) response.getBody()).getBytes()));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        */

//        SupportMessageReply smr = new SupportMessageReply();
//        smr.setUsername("하하하");
//        smr.setContent("sdfsdfsdf<sdfs>");
//        smr.setMid(6);
//
//        writeMessageReply(6, smr, new Callback<Response>() {
//            @Override
//            public void success(Response response, Response response2) {
//                Log.d(LOG_TAG, "writeMessageReplyTest");
//                Log.d(LOG_TAG, new String(((TypedByteArray) response.getBody()).getBytes()));
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });

    }




}
