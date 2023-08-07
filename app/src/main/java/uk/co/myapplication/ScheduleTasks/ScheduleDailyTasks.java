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

public class ScheduleDailyTasks extends BroadcastReceiver {
    private Context context;
    private Content content;
    public static final String NOTIFICATION_CHANNEL_ID = "10000" ;
    private final static String default_notification_channel_id = "default" ;
    private ArrayList<Content> contents,eventContents;
    private static ContentDatabase database;
    int id,notificationId;
    int priority;
    String title,time;
    boolean isEvent;
    Dates dates =new Dates();
    public static int counter=-1;
    public static AlarmManager alarmManager;
    public static ArrayList<PendingIntent> pendingIntents=new ArrayList<PendingIntent>();

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        database = new ContentDatabase(context);
        deleteEvents();
        deleteTemporaryAndEvents();
        addDailyNotifications();
    }

    private void deleteEvents() {
        eventContents=database.getContents("EVENT");
        for(Content content:eventContents){
            try {
                if(new SimpleDateFormat("dd/MM/yyyy").parse(content.getDate()).before(new Date())){
                    database.deleteContent(content.getID(),"EVENT");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDailyNotifications() {
        contents=database.getContents(dates.getToday());
        for(Content content:contents) {
            if(getTimeCalendar(content).compareTo(getCurrentTime()) > 0){
                scheduleNotification(buildNotification(content),getTime(content));
                database.editContent(content, dates.getToday());
            }
        }
    }

    protected Date getTimeCalendar(Content content) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(convertStringToDateAndTime(content.getTimeStart(),dates.getTodayDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    protected Date getCurrentTime(){
        return Calendar.getInstance().getTime();
    }


    long getTime(Content content) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(convertStringToDateAndTime(content.getTimeStart(),dates.getTodayDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    private void deleteTemporaryAndEvents() {
        String day=dates.getToday();
        day=dates.getPreviousDay(day);
        contents=database.getContents(day);
        for(Content content:contents){
            if(!(content.isPermanent()) && content.isEvent()){
                database.deleteContent(content.getID(),day);
            }
        }
    }
    protected void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent( context, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        notificationIntent.putExtra("Title",title);
        notificationIntent.putExtra("Time",time);
        notificationIntent.putExtra("Priority",priority);
        notificationIntent.putExtra("isEvent",isEvent);
        notificationIntent.putExtra("requestCode",counter+10);
        Log.d("MyAlarmSystem", "-> Daily");
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
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , delay , pendingIntent); ;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP , delay , pendingIntent); ;
        } else {
            // Inexact time was only introduced in API 19+. Before that, all was exact time
            alarmManager.set(AlarmManager.RTC_WAKEUP , delay , pendingIntent) ;
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
    public static Date convertStringToDateAndTime(String time,String date) throws ParseException{
        return new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date+" "+time);
    }

    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("kk:mm").parse(time);
    }
}
