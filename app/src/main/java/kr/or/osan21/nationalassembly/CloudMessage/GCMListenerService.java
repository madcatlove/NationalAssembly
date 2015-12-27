package kr.or.osan21.nationalassembly.CloudMessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import kr.or.osan21.nationalassembly.R;
import kr.or.osan21.nationalassembly.SplashActivity;

/**
 * Created by madcat on 12/27/15.
 */
public class GCMListenerService extends GcmListenerService {

    private static final String LOG_TAG = "GCMListenerService";
    public static final int NOTIFY_ID = 99887;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(LOG_TAG, " Message Arrived " + from);

        final String title = data.getString("title", "");
        final String content = data.getString("content", "");

        noti(title, content);

    }

    private void noti(String title, String content) {

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("공지사항이 도착했습니다.");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);


        nm.notify(NOTIFY_ID, builder.build());
    }
}
