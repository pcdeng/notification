package com.pcdeng.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationProvider {
    private static final String TASK_CHANNEL_ID = "TASK_CHANNEL_ID";
    private static final String TASK_CHANNEL_DISPLAY_NAME = "Task";
    private static final String CHAT_CHANNEL_ID = "CHAT_CHANNEL_ID";
    private static final String CHAT_CHANNEL_DISPLAY_NAME = "Chat";

    /**
     * 自定义推送声音
     * Android 8 之后增加了 channel
     * 详细看 https://developer.android.com/guide/topics/ui/notifiers/notifications#ManageChannels
     * @param context 上下文
     */
    public static void showNotification(Context context, NotificationContent content) {
        Log.d("Notification", "processCustomMessage:" + content.getSound());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            handleAndroid8Notification(context, content);
            Log.d("[Notification]", "Android API level >= 26");
        } else {
            handleAndroidNotification(context, content);
            Log.d("[Notification]", "Android API level < 26");
        }
    }

    /**
     * 处理 Android 版本 8（含，对应 API level 26)的通知处理
     * Task 相关对应的铃声是 quantum_bell
     * Chat 相关的通知对应铃声是 quantum_ding
     * @param context 上下文，对应是
     * @param content 通知内容（自定义格式，详细看 NotificationContent 类）
     */
    public static void handleAndroid8Notification(Context context, NotificationContent content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String chanelId = content.getChannelId();
        NotificationCompat.Builder builder = buildNotification(context, content, chanelId);
        manager.notify(1, builder.build());
    }

    public static void initTaskChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(TASK_CHANNEL_ID, TASK_CHANNEL_DISPLAY_NAME, NotificationManager.IMPORTANCE_HIGH);
            Uri url = Uri.parse("android.resource://" + context.getPackageName() + "/raw/quantum_bell");
            Log.d("chat channel sound", url.toString());
            channel.setSound(url, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            manager.createNotificationChannel(channel);
        }
    }

    public static void initChatChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHAT_CHANNEL_ID, CHAT_CHANNEL_DISPLAY_NAME, NotificationManager.IMPORTANCE_HIGH);
            Uri url = Uri.parse("android.resource://" + context.getPackageName() + "/raw/quantum_ding");
            Log.d("chat channel sound", url.toString());
            channel.setSound(url, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * 处理 Android 版本 8（不含8，对应 API level 26)以下的通知处理
     * @param context
     * @param content
     */
    private static void handleAndroidNotification(Context context, NotificationContent content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = buildNotification(context, content, null);
        Uri url = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + content.getSound());
        builder.setSound(url);
        manager.notify(1, builder.build());
    }

    /**
     * 构建 notification
     * @param context
     * @param content
     * @param channelId
     * @return
     */
    private static NotificationCompat.Builder buildNotification(Context context, NotificationContent content, String channelId) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);

        notification.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(content.getContent())
                .setContentTitle(content.getTitle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmap);

        return notification;
    }
}