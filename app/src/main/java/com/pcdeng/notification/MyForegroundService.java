package com.pcdeng.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyForegroundService extends Service {

    private NotificationManager notificationManager;
    private String notificationId = "stickyChannelId";
    private String notificationName = "Sticky Channel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyForegroundService", "onCreate");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建 NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1, getNotification());
        Log.d("MyForegroundService", "startForeground, id is 1");
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Stayplease")
                .setOngoing(true);
        // 设置 Notification 的 ChannelID ,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    @Override
    public void onDestroy() {
        Log.d("MyForegroundService", "onDestroy()");
        stopForeground(true); // 停止前台服务--参数：表示是否移除之前的通知
        super.onDestroy();
    }
}