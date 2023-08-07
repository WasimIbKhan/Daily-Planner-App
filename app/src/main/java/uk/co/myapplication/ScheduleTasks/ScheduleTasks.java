package uk.co.myapplication.ScheduleTasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import uk.co.myapplication.Model.Dates;

public class ScheduleTasks {
    private Context context;
    Dates dates= new Dates();

    public ScheduleTasks(Context context){
        this.context=context;
    }

    public void ScheduleDailyTasks(){
        Intent intent = new Intent( context, ScheduleDailyTasks.class) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 0 , intent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,getDailyTime(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void ScheduleEventTasks(){
        Intent intent = new Intent( context, ScheduleEventTasks.class) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 1 , intent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,getWeeklyTime(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
    }

    public void ScheduleCancel(){
        Intent intent = new Intent( context, CancelTasks.class) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 2 , intent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,getLastSecond(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private long getLastSecond() {


        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.HOUR,23);
        myCalendar.set(Calendar.MINUTE, 59);
        myCalendar.set(Calendar.SECOND, 59);
        myCalendar.set(Calendar.MILLISECOND, 0);
        Log.d("MyAlarmSystem", "-> Time: "+myCalendar.getTime());
        return myCalendar.getTimeInMillis();
    }

    private long getWeeklyTime() {
        Calendar myCalendar = Calendar. getInstance () ;
        myCalendar.add(Calendar.DATE, +7);
        myCalendar.set(Calendar.DAY_OF_WEEK,2);
        myCalendar.set(Calendar.HOUR,0);
        myCalendar.set(Calendar.MINUTE, 0);
        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);
        Log.d("MyAlarmSystem", "-> Time: "+myCalendar.getTime());
        return myCalendar.getTimeInMillis();
    }

    private long getDailyTime() {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.DATE, +1);
        myCalendar.set(Calendar.HOUR,0);
        myCalendar.set(Calendar.MINUTE, 0);
        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);
        Log.d("MyAlarmSystem", "-> Time: "+myCalendar.getTime());
        return myCalendar.getTimeInMillis();
    }

    public Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yy").parse(date);
    }
}
