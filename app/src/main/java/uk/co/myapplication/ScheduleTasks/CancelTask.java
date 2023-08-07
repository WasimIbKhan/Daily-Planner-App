package uk.co.myapplication.ScheduleTasks;

import android.content.Context;
import android.content.Intent;

import uk.co.myapplication.Model.Content;

public class CancelTask extends ScheduleCurrentTask {

    Context context;
    Content content;

    public CancelTask(Context context, Content content){
        super(context,content);
        this.context=context;
        this.content=content;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }


    public void deleteScheduledNotification() {
        alarmManager.cancel(pendingIntents.get(content.getNotificationID()));
        pendingIntents.remove(content.getNotificationID());
    }
}
