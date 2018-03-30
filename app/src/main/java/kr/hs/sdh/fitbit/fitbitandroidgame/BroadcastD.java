package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastD extends BroadcastReceiver {
    beforeGPS bg = new beforeGPS();
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.c).setTicker("testTicker").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("testTitle").setContentText("testText")
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent).setAutoCancel(true);
        notificationManager.notify(1,builder.build());
        bg.startAlarm();
    }
}
