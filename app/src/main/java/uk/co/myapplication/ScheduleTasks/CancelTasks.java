package uk.co.myapplication.ScheduleTasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.Model.Dates;

import static uk.co.myapplication.ScheduleTasks.ScheduleDailyTasks.pendingIntents;
import static uk.co.myapplication.ScheduleTasks.ScheduleDailyTasks.alarmManager;
public class CancelTasks extends BroadcastReceiver {
    Dates dates =new Dates();
    private static ContentDatabase database;
    private ArrayList<Content> contents;
    @Override
    public void onReceive(Context context, Intent intent) {
        database=new ContentDatabase(context);
        contents=database.getContents(dates.getToday());
        if(pendingIntents.size()<0){
            for(int i=0; i<pendingIntents.size(); i++){
                alarmManager.cancel(pendingIntents.get(i));
            }
            pendingIntents.clear();
        }
    }
}
