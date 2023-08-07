package uk.co.myapplication.ScheduleTasks;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import uk.co.myapplication.Model.Content;
import uk.co.myapplication.Model.Dates;

public class AddScheduleTask extends ScheduleCurrentTask{
    Context context;
    Content content;
    String day;
    Dates dates=new Dates();

    public AddScheduleTask(Context context, Content content,String day){
        super(context,content);
        this.context=context;
        this.content=content;
        this.day=day;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void addNotification(){
        if(getTimeCalendar(content).compareTo(getCurrentTime()) > 0 && dates.getDate(day).equals(dates.getTodayDate())){
            Log.d("MyAlarmSystem", "-> "+day);
            scheduleNotification(buildNotification(content),getTime(content));
        }
    }

    public int getNotificationID(){
        return notificationId;
    }

}
