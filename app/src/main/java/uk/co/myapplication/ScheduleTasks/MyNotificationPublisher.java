package uk.co.myapplication.ScheduleTasks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static uk.co.myapplication.ScheduleTasks.ScheduleDailyTasks.alarmManager;
import static uk.co.myapplication.ScheduleTasks.ScheduleDailyTasks.pendingIntents;

public class MyNotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    String title,time;
    int priority,requestCode,pendingIndex;
    boolean isEvent;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        title=intent.getStringExtra("Title");
        time=intent.getStringExtra("Time");
        priority=intent.getIntExtra("Priority",0);
        isEvent=intent.getBooleanExtra("isEvent",false);
        requestCode=intent.getIntExtra("requestCode",3);
        pendingIndex=intent.getIntExtra("Pending Index",0);
        Log.d("MyAlarmSystem", "-> 2.Publisher");

        if(isEvent){
            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( "10001" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                assert notificationManager != null;
                notificationManager.createNotificationChannel(notificationChannel) ;
            }
        }
        else{
            switch (priority){
                case 0:
                    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                        int importance = NotificationManager. IMPORTANCE_LOW ;
                        NotificationChannel notificationChannel = new NotificationChannel( "10000" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                        assert notificationManager != null;
                        notificationManager.createNotificationChannel(notificationChannel) ;
                        cancelPendingIntent(pendingIndex,requestCode);
                    }
                case 1:
                    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                        int importance = NotificationManager. IMPORTANCE_HIGH ;
                        NotificationChannel notificationChannel = new NotificationChannel( "10000" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                        assert notificationManager != null;
                        notificationManager.createNotificationChannel(notificationChannel) ;
                        cancelPendingIntent(pendingIndex,requestCode);
                    }
                case 2:
                    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                        int importance = NotificationManager. IMPORTANCE_HIGH ;
                        NotificationChannel notificationChannel = new NotificationChannel( "10000" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                        assert notificationManager != null;
                        notificationManager.createNotificationChannel(notificationChannel) ;
                        cancelPendingIntent(pendingIndex,requestCode);
                    }
                case 3:
                    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                        int importance = NotificationManager. IMPORTANCE_LOW ;
                        NotificationChannel notificationChannel = new NotificationChannel( "10000" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                        assert notificationManager != null;
                        notificationManager.createNotificationChannel(notificationChannel) ;
                        cancelPendingIntent(pendingIndex,requestCode);
                        Intent intent1 = new Intent(context,PopUpAlarm.class);
                        Log.d("MyAlarmSystem", "-> 3.Priority");
                        intent1.putExtra("Title",title);
                        intent1.putExtra("Time",time);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    }
            }
        }
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , notification) ;
    }

    private void cancelPendingIntent(int pendingIndex, int requestCode) {
         alarmManager.cancel(pendingIntents.get(pendingIndex));
        Log.d("RequestCode", "-> "+requestCode);
    }
}
