package uk.co.myapplication.Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Content implements Comparable<Content>, Serializable {
    private long ID;
    private int activity,priority,notificationID;
    private String Title,timeStart,timeEnd,date,description,address;
    private boolean isPermanent,isEvent,expandable,expandable2;

    public Content(){this.expandable=false; this.expandable2=false;}

    public Content(String Title, int activity, int priority, String timeStart,String timeEnd, String description, boolean isPermanent, boolean isEvent,String address,int notificationID){
        this.Title=Title;
        this.activity=activity;
        this.priority=priority;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.description=description;
        this.isPermanent = isPermanent;
        this.isEvent=isEvent;
        this.address=address;
        this.notificationID=notificationID;
        this.expandable=false;
        this.expandable2=false;
    }
    public Content(String Title,int activity, String timeStart,String timeEnd,String date,String description, boolean isPermanent,boolean isEvent,String address,int notificationID){
        this.Title=Title;
        this.activity=activity;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.date=date;
        this.description=description;
        this.isPermanent = isPermanent;
        this.isEvent=isEvent;
        this.address=address;
        this.notificationID=notificationID;
        this.expandable=false;
        this.expandable2=false;
    }
    public Content(long ID, String Title,int activity,int priority, String timeStart,String timeEnd , String description, boolean isPermanent,boolean isEvent,String address,int notificationID){
        this.ID=ID;
        this.Title=Title;
        this.activity=activity;
        this.priority=priority;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.description=description;
        this.isPermanent = isPermanent;
        this.isEvent=isEvent;
        this.address=address;
        this.notificationID=notificationID;
        this.expandable=false;
        this.expandable2=false;
    }
    public Content(long ID, String Title,int activity, String timeStart,String timeEnd, String date, String description, boolean isPermanent, boolean isEvent,String address,int notificationID){
        this.ID=ID;
        this.Title=Title;
        this.activity=activity;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.date=date;
        this.description=description;
        this.isPermanent = isPermanent;
        this.isEvent=isEvent;
        this.address=address;
        this.notificationID=notificationID;
        this.expandable=false;
        this.expandable2=false;
    }
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String time) {
        this.timeStart = time;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public boolean isPermanent() { return isPermanent; }

    public void setPermanent(boolean permanent) { isPermanent = permanent; }

    public boolean isEvent() { return isEvent; }

    public void setEvent(boolean event) { isEvent = event; }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public boolean isExpandable2() {
        return expandable2;
    }

    public void setExpandable2(boolean expandable2) {
        this.expandable2 = expandable2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isExpandable() { return expandable; }

    public void setExpandable(boolean expandable) { this.expandable = expandable; }
    @Override
    public int compareTo(Content c) {
        try {
            return convertStringToTime(this.timeStart).compareTo(convertStringToTime(c.getTimeStart()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Date convertStringToTime(String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date d = dateFormat.parse(time);
        return d;
    }


}
