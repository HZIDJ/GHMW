package com.mediatek.game.notifications;

import android.app.NotificationChannel;
import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;

/**
 * Notification Manager - Issue #1, #4, #8
 * Manages game notifications with proper alerting
 * Ensures notifications work during active gaming
 */
public class NotificationManager {
    
    private Context context;
    private android.app.NotificationManager notificationManager;
    public static final String CHANNEL_ID = "game_notifications";
    public static final String URGENT_CHANNEL_ID = "game_urgent";
    
    public NotificationManager(Context context) {
        this.context = context;
        this.notificationManager = 
            (android.app.NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        createNotificationChannels();
    }
    
    /**
     * Create notification channels with proper alert settings - Issue #1, #4, #8
     */
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Normal notifications channel
            NotificationChannel normalChannel = new NotificationChannel(
                CHANNEL_ID,
                "Game Notifications",
                android.app.NotificationManager.IMPORTANCE_DEFAULT);
            
            normalChannel.setDescription("Normal game notifications");
            normalChannel.enableVibration(true);
            
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
            
            normalChannel.setSound(
                android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                audioAttributes);
            
            notificationManager.createNotificationChannel(normalChannel);
            
            // Urgent notifications channel - Issue #8
            NotificationChannel urgentChannel = new NotificationChannel(
                URGENT_CHANNEL_ID,
                "Urgent Game Alerts",
                android.app.NotificationManager.IMPORTANCE_HIGH);
            
            urgentChannel.setDescription("Urgent game alerts");
            urgentChannel.enableVibration(true);
            urgentChannel.setVibrationPattern(new long[]{0, 250, 250, 250});
            
            urgentChannel.setSound(
                android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                audioAttributes);
            
            notificationManager.createNotificationChannel(urgentChannel);
        }
    }
    
    /**
     * Post notification that works during gameplay - Issue #1, #4
     */
    public void postGameNotification(int notificationId, 
        androidx.core.app.NotificationCompat.Builder builder) {
        
        builder.setChannelId(CHANNEL_ID)
            .setShowWhen(true)
            .setAutoCancel(true)
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT);
        
        notificationManager.notify(notificationId, builder.build());
    }
    
    /**
     * Post urgent notification - Issue #1, #8
     */
    public void postUrgentNotification(int notificationId, 
        androidx.core.app.NotificationCompat.Builder builder) {
        
        builder.setChannelId(URGENT_CHANNEL_ID)
            .setShowWhen(true)
            .setAutoCancel(true)
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(null, true);
        
        notificationManager.notify(notificationId, builder.build());
    }
}
