package com.ibm.mymedicalapp.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class AlarmScheduler {

    public void setAlarm(Context context, long alarmTime, int alarmNumID, String title) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        PendingIntent operation = PendingIntent.getBroadcast(context, alarmNumID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 23) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, operation);
        } else {
            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, operation);
        }
    }

    public void setRepeatAlarm(Context context, long alarmTime, int alarmNumID, long RepeatTime, String title) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        PendingIntent operation = PendingIntent.getBroadcast(context, alarmNumID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, RepeatTime, operation);
    }

    public void cancelAlarm(Context context, int alarmNumID) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent operation = PendingIntent.getBroadcast(context, alarmNumID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.cancel(operation);
    }

}
