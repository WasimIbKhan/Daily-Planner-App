package uk.co.myapplication.ScheduleTasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.Model.Dates;
import uk.co.myapplication.R;

public class ScheduleEventTasks extends BroadcastReceiver {
    private Context context;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private ArrayList<Content> eventContents;
    private static ContentDatabase database;
    int priority;
    String title,time;
    boolean isEvent;
    Dates dates =new Dates();
    String [] allDates;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        database= new ContentDatabase(context);
        ScheduleMondayEventNotifications();
        ScheduleTomorrowsEvent();
    }

    private void ScheduleTomorrowsEvent() {
        Calendar calendar = Calendar.getInstance();
        allDates=dates.getDates();
        eventContents=database.getContents("EVENT");
        for(Content content:eventContents){
            calendar.set(Calendar.DAY_OF_WEEK,4);
            for(int i = 0; i < 5;i++){
                calendar.set(Calendar.HOUR,0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                scheduleNotification(buildEventNotification(content),calendar.getTimeInMillis());
                calendar.add(Calendar.DATE, +1);
            }
        }
    }

    private void ScheduleMondayEventNotifications() {
        allDates=dates.getDates();
        eventContents=database.getContents("EVENT");
        for(String date:allDates){
            for(Content content:eventContents){
                try {
                    if(convertStringToDate(date).compareTo(convertStringToDate(content.getDate()))==0){
                        scheduleNotification(buildEventNotification(content),getMondayTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent( context, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        notificationIntent.putExtra("Title",title);
        notificationIntent.putExtra("Time",time);
        notificationIntent.putExtra("Priority",priority);
        notificationIntent.putExtra("isEvent",isEvent);
        Log.d("MyAlarmSystem", "-> Events");
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 3 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if you don't want the alarm to go off even in Doze mode, use
            // setExact instead
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , delay , pendingIntent); ;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP , delay , pendingIntent); ;
        } else {
            // Inexact time was only introduced in API 19+. Before that, all was exact time
            alarmManager.set(AlarmManager.RTC_WAKEUP , delay , pendingIntent) ;
        }
    }
    private Notification buildEventNotification (Content content) {
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
    private long getMondayTime() {
        Calendar myCalendar = Calendar. getInstance () ;
        myCalendar.set(Calendar.DAY_OF_WEEK,2);
        myCalendar.set(Calendar.HOUR,0);
        myCalendar.set(Calendar.MINUTE, 0);
        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);
        return myCalendar.getTimeInMillis();
    }
    public static Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yy").parse(date);
    }
}
