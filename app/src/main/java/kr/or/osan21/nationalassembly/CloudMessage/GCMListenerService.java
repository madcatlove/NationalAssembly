package kr.or.osan21.nationalassembly.CloudMessage;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.List;

import kr.or.osan21.nationalassembly.MainActivity;
import kr.or.osan21.nationalassembly.NoticeActivity;
import kr.or.osan21.nationalassembly.NoticeContentActivity;
import kr.or.osan21.nationalassembly.R;

/**
 * Created by madcat on 12/27/15.
 */
public class GCMListenerService extends GcmListenerService {

    private static final String LOG_TAG = "GCMListenerService";
    public static final int NOTIFY_ID = 99887;

    @Override
    public void onMessageReceived(String from, Bundle data) {


        final String notice_id = data.getString("notice_id", "0");
        final String title = data.getString("title", "");
        final String content = data.getString("content", "");

        Log.d(LOG_TAG, " Message Arrived " + from + " / " + String.format("%s,%s,%s", notice_id, title, content));

        noti(notice_id, title, content);

    }

    private void noti(String notice_id, String title, String content) {

        SharedPreferences sharedPreferences = getSharedPreferences(CONST_PUSH_MESSAGE.PUSH_SHARED_PREF_STR, MODE_PRIVATE);

        // 푸시 허용상태가 아니라면 무시
        final String PUSH_STATUS = sharedPreferences.getString(CONST_PUSH_MESSAGE.PUSH_STATUS_KEY, CONST_PUSH_MESSAGE.PUSH_ON);
        if( PUSH_STATUS.equals(CONST_PUSH_MESSAGE.PUSH_OFF) ) {
            Log.d(LOG_TAG, ">> GCM Received BUT (push off) ignore notification");
            return;
        }


        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = null;

        // 메인엑티비티 -> 노티스리스트 액티비티 -> 노티스 컨텐츠
        if( !isActivityRunning() ) {
            Log.i(LOG_TAG, " ACTIVITY IS NOT RUNNING!! ");
            Intent mainActivity = new Intent(this, MainActivity.class);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent noticeListActivity = new Intent(this, NoticeActivity.class);
            noticeListActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent noticeContentActivity = new Intent(this, NoticeContentActivity.class);
            noticeContentActivity.putExtra("n_id", Integer.valueOf(notice_id));
            noticeContentActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            pendingIntent = PendingIntent.getActivities(this, 0x88, new Intent[] {
                    mainActivity, noticeListActivity, noticeContentActivity
            }, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else {
            Log.i(LOG_TAG, " ACTIVITY IS RUNNING!! ");
            Intent noticeContentActivity = new Intent(this, NoticeContentActivity.class);
            noticeContentActivity.putExtra("n_id", Integer.valueOf(notice_id));
            noticeContentActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            pendingIntent = PendingIntent.getActivity(this, 0x88, noticeContentActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        }


        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.app_icon);

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


    // 내 앱이 실행중인가
    private boolean isActivityRunning() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> taskInfo = am.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo info : taskInfo) {
            if( info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ) {
                if( info.processName.equals(getPackageName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
