package kr.or.osan21.nationalassembly.Utils;

import retrofit.RestAdapter;

/**
 * Created by madcat on 12/27/15.
 */
public class API  {

    // TESTING
    // http://assembly.my.n-pure.net/
//    public static final String API_URL = "http://assembly.my.n-pure.net/index.php/";
//    public static final String STATIC_URL = "http://ec2-52-192-93-201.ap-northeast-1.compute.amazonaws.com/static/";
//    public static final String UPLOAD_URL = "http://assembly.my.n-pure.net/upload/";
    public static final String YOUTUBE_DEV_KEY = "AIzaSyA1PhfQKbQKnriHu4fo4vZxnQkGBPDn8sE";

    // PRODUCTION
    public static final String API_URL = "http://ec2-52-192-93-201.ap-northeast-1.compute.amazonaws.com/index.php/";
    public static final String STATIC_URL = "http://ec2-52-192-93-201.ap-northeast-1.compute.amazonaws.com/static/";
    public static final String UPLOAD_URL = "http://ec2-52-192-93-201.ap-northeast-1.compute.amazonaws.com/upload/";

    protected RestAdapter mRestAdapter;

    public API() {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }
}
