package kr.or.osan21.nationalassembly.Utils;

import retrofit.RestAdapter;

/**
 * Created by madcat on 12/27/15.
 */
public class API  {

    public static final String API_URL = "http://assembly.my.n-pure.net/index.php/";
    protected RestAdapter mRestAdapter;

    public API() {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }
}
