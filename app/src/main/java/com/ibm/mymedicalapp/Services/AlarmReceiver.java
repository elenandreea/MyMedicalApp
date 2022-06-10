package com.ibm.mymedicalapp.Services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.ibm.mymedicalapp.Activities.AddReminderActivity;
import com.ibm.mymedicalapp.R;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String message = "Take your " + intent.getExtras().getString("title");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Intent action = new Intent(context, AddReminderActivity.class);
        PendingIntent operation = PendingIntent.getActivity(context, 0 , action,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "notification_channel";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.notification_logo)
                        .setContentTitle("Alarm Reminder")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setContentIntent(operation);


        // For android Oreo and above notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Alarm notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 , notificationBuilder.build());

    }
}
