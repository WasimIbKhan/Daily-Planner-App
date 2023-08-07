package uk.co.myapplication.Model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import uk.co.myapplication.R;

public class Dates {
    private static final int[] TAB_DAYS= new int[]{R.string.Monday, R.string.Tuesday,R.string.Wednesday, R.string.Thursday,R.string.Friday, R.string.Saturday,R.string.Sunday};
    private static final String[] TAB_TITLES = new String[7];
    private String monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    private String[] Dates=new String[7];
    private Context context;
    public Dates(Context context){
        this.context=context;
    }
    public Dates(){
        Calendar calendar= Calendar.getInstance();
        String[] calendarDays = new String[7];
        String[] calendarDates= new String[7];
        String [] dayName={"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
        for(int i = 0; i < 7;i++){
            calendarDays[i]=getDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK));
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            calendarDates[i] = format1.format(calendar.getTime());
            calendar.add(Calendar.DATE, +1);
        }
        for(int i=0;i <7;i++) {
            for(int j=0;j<7;j++) {
                if(dayName[i].equalsIgnoreCase(calendarDays[j])) {
                    Dates[i]=calendarDates[j];
                }
            }
        }
        setMonday();
        setTuesday();
        setWednesday();
        setThursday();
        setFriday();
        setSaturday();
        setSunday();
    }

    public String getDate(String day){
        switch(day){
            case "MONDAY":
                return getMonday();
            case "TUESDAY":
                return getTuesday();
            case "WEDNESDAY":
                return  getWednesday();
            case "THURSDAY":
                return getThursday();
            case "FRIDAY":
                return getFriday();
            case "SATURDAY":
                return getSaturday();
            case "SUNDAY":
                return getSunday();
            default:
                return null;
        }
    }
    public String getMonday() {
        return monday;
    }

    public void setMonday() {
        this.monday = Dates[0];
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday() {
        this.tuesday = Dates[1];
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday() {
        this.wednesday = Dates[2];
    }

    public String getThursday() {
        return thursday;
    }
    public void setThursday() {
        this.thursday = Dates[3];
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday() {
        this.friday = Dates[4];
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday() {
        this.saturday = Dates[5];
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday() {
        this.sunday = Dates[6];
    }

    public String[] getDates(){ return Dates; }
    public String TabTitles(int position){
        Calendar calendar = Calendar.getInstance();
        String[] calendarDays = new String[7];
        String[] calendarDates= new String[7];
        String[] calendarMonths= new String[7];
        for(int i = 0; i < 7;i++){
            calendarDays[i]=getDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK));
            calendarDates[i]=""+calendar.get(Calendar.DAY_OF_MONTH);
            calendarMonths[i]=theMonth(calendar.get(Calendar.MONTH));
            calendar.add(Calendar.DATE, +1);
        }
        for(int i=0;i <7;i++) {
            for(int j=0;j<7;j++) {
                if(context.getResources().getString(TAB_DAYS[i]).equalsIgnoreCase(calendarDays[j])) {
                    TAB_TITLES[i]=context.getResources().getString(TAB_DAYS[i])+"\n"+pad(Integer.parseInt(calendarDates[j])) +" " +calendarMonths[j];
                }
            }
        }
        return TAB_TITLES[position];
    }
    public String getToday(){
        Calendar calendar = Calendar.getInstance();
        String day="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            day=""+ LocalDate.now().getDayOfWeek();
        }else{
            System.out.println(day);
            day= getDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK));
        }
        return day;
    }

    public String getTodayDate(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        return format1.format(calendar.getTime());
    }


    public String getPreviousDay(String day){
        switch(day){
            case "MONDAY":
                return "SUNDAY";
            case "TUESDAY":
                return "MONDAY";
            case "WEDNESDAY":
                return  "TUESDAY";
            case "THURSDAY":
                return "WEDNESDAY";
            case "FRIDAY":
                return "THURSDAY";
            case "SATURDAY":
                return "FRIDAY";
            case "SUNDAY":
                return "SATURDAY";
            default:
                return null;
        }
    }
    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }


    private String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
    private String getDayOfTheWeek(int day) {
        String [] dayName={"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        return dayName[day-1];
    }
}
