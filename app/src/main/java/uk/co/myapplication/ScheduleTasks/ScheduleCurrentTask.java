package uk.co.myapplication.ScheduleTasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;

public class ScheduleCurrentTask extends ScheduleDailyTasks {
    private final static String default_notification_channel_id = "default" ;
    Context context;
    Content content;
    public ScheduleCurrentTask(Context context, Content content) {
        this.context=context;
        this.content=content;
    }

    protected void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent( context, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        notificationIntent.putExtra("Title",title);
        notificationIntent.putExtra("Time",time);
        notificationIntent.putExtra("Priority",priority);
        notificationIntent.putExtra("isEvent",isEvent);
        //notificationIntent.putExtra("requestCode",counter+10);
        Log.d("MyAlarmSystem", "-> Current");
        counter++;
        content.setNotificationID(counter);
        notificationId=content.getNotificationID();
        notificationIntent.putExtra("Pending Index",counter);
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, notificationId+10 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        pendingIntents.add(pendingIntent);
        alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if you don't want the alarm to go off even in Doze mode, use
            // setExact instead
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC , delay , pendingIntent); ;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC , delay , pendingIntent); ;
        } else {
            // Inexact time was only introduced in API 19+. Before that, all was exact time
            alarmManager.set(AlarmManager.RTC , delay , pendingIntent) ;
        }

    }
    Notification buildNotification(Content content) {
        id=(int) content.getID();
        title=content.getTitle();
        time=content.getTimeStart();
        priority=content.getPriority();
        isEvent=content.isEvent();
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context,
                default_notification_channel_id ) ;
        builder.setContentTitle( content.getTitle()) ;
        builder.setContentText(content.getDescription()) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
}
