package uk.co.myapplication.ScheduleTasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import uk.co.myapplication.Model.Content;
import uk.co.myapplication.Model.Dates;

public class EditNotificationSchedule extends ScheduleCurrentTask {
    Context context;
    Content content;
    String day;
    Dates dates=new Dates();

    public EditNotificationSchedule(Context context, Content content,String day){
        super(context,content );
        this.context=context;
        this.content=content;
        this.day=day;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void editNotification(){
        if(getTimeCalendar(content).compareTo(getCurrentTime()) > 0 && dates.getDate(day).equals(dates.getTodayDate())){
            Log.d("MyAlarmSystem", "-> "+day);
            scheduleNotification(buildNotification(content),getTime(content),content.getNotificationID()+10);
        }
    }

    protected void scheduleNotification (Notification notification , long delay, int requestCode){
        Intent notificationIntent = new Intent( context, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        notificationIntent.putExtra("Title",title);
        notificationIntent.putExtra("Time",time);
        notificationIntent.putExtra("Priority",priority);
        notificationIntent.putExtra("isEvent",isEvent);
        Log.d("MyAlarmSystem", "-> ChangeNotification");
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, requestCode , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if you don't want the alarm to go off even in Doze mode, use
            // setExact instead
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC , delay , pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC , delay , pendingIntent);
        } else {
            // Inexact time was only introduced in API 19+. Before that, all was exact time
            alarmManager.set(AlarmManager.RTC , delay , pendingIntent) ;
        }
    }


}
