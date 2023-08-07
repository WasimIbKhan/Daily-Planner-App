package uk.co.myapplication.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import uk.co.myapplication.Model.Content;


public class ContentDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contentlist";

    //all tables
    private static final String EVENT_TABLE = "eventtable";
    private static final String TEMPORARY_TABLE="temporarytable";
    private static final String MONDAY_TABLE = "mondaytable";
    private static final String TUESDAY_TABLE = "tuesdaytable";
    private static final String WEDNESDAY_TABLE = "wednesdaytable";
    private static final String THURSDAY_TABLE = "thursdaytable";
    private static final String FRIDAY_TABLE = "fridaytable";
    private static final String SATURDAY_TABLE = "saturdaytable";
    private static final String SUNDAY_TABLE = "sundaytable";

    //columns name for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ACTIVITY="activity";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_TIME_START = "timestart";
    private static final String KEY_TIME_END = "timeend";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PERMANENT="permanent";
    private static final String KEY_EVENT="event";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_NOTIFICATION_ID="notification";

    public ContentDatabase(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables with columns(id INT PRIMARY KEY,title,TEXT,content text,date TEXT,time TEXT)
        String eventQuery = "CREATE TABLE " + EVENT_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT, " +
                KEY_ACTIVITY +" TEXT," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String temporaryQuery="CREATE TABLE " + TEMPORARY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT, " +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String mondayQuery = "CREATE TABLE " + MONDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT," +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String tuesdayQuery = "CREATE TABLE " + TUESDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT," +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String wednesdayQuery = "CREATE TABLE " + WEDNESDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT," +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String thursdayQuery = "CREATE TABLE " + THURSDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT," +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String fridayQuery = "CREATE TABLE " + FRIDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT, " +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String saturdayQuery = "CREATE TABLE " + SATURDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT," +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";
        String sundayQuery = "CREATE TABLE " + SUNDAY_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT," +
                KEY_ACTIVITY +" TEXT," +
                KEY_PRIORITY+ " INTEGER," +
                KEY_TIME_START + " TEXT," +
                KEY_TIME_END + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_PERMANENT +" BOOLEAN," +
                KEY_EVENT +" BOOLEAN," +
                KEY_ADDRESS +" TEXT," +
                KEY_NOTIFICATION_ID +" INTEGER" +
                " )";

        db.execSQL(eventQuery);
        db.execSQL(temporaryQuery);
        db.execSQL(mondayQuery);
        db.execSQL(tuesdayQuery);
        db.execSQL(wednesdayQuery);
        db.execSQL(thursdayQuery);
        db.execSQL(fridayQuery);
        db.execSQL(saturdayQuery);
        db.execSQL(sundayQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion){
         db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TEMPORARY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MONDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TUESDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WEDNESDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + THURSDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FRIDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SATURDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUNDAY_TABLE);
        onCreate(db);
        }




    }

    public void  addContent(Content content, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE,content.getTitle());
        c.put(KEY_ACTIVITY,content.getActivity());
        c.put(KEY_PRIORITY,content.getPriority());
        c.put(KEY_TIME_START,content.getTimeStart());
        c.put(KEY_TIME_END,content.getTimeEnd());
        c.put(KEY_DESCRIPTION,content.getDescription());
        c.put(KEY_PERMANENT,content.isPermanent());
        c.put(KEY_EVENT,content.isEvent());
        c.put(KEY_ADDRESS,content.getAddress());
        c.put(KEY_NOTIFICATION_ID,content.getNotificationID());

        ContentValues c2 = new ContentValues();
        c2.put(KEY_TITLE,content.getTitle());
        c2.put(KEY_ACTIVITY,content.getActivity());
        c2.put(KEY_TIME_START,content.getTimeStart());
        c2.put(KEY_TIME_END,content.getTimeEnd());
        c2.put(KEY_DATE,content.getDate());
        c2.put(KEY_DESCRIPTION,content.getDescription());
        c2.put(KEY_PERMANENT,content.isPermanent());
        c2.put(KEY_EVENT,content.isEvent());
        c2.put(KEY_ADDRESS,content.getAddress());
        c2.put(KEY_NOTIFICATION_ID,content.getNotificationID());

        switch(day){
            case "MONDAY":
                db.insert(MONDAY_TABLE,null,c);
                break;
            case "TUESDAY":
                db.insert(TUESDAY_TABLE,null,c);
                break;
            case "WEDNESDAY":
                db.insert(WEDNESDAY_TABLE,null,c);
                break;
            case "THURSDAY":
                db.insert(THURSDAY_TABLE,null,c);
                break;
            case "FRIDAY":
                db.insert(FRIDAY_TABLE,null,c);
                break;
            case "SATURDAY":
                db.insert(SATURDAY_TABLE,null,c);
                break;
            case "SUNDAY":
                db.insert(SUNDAY_TABLE,null,c);
                break;
            case "TEMPORARY":
                db.insert(TEMPORARY_TABLE,null,c);
                break;
            case "EVENT":
                db.insert(EVENT_TABLE,null,c2);
                break;

        }
        db.close();
        //long ID = db.insert(EVENT_TABLE,null,c);
        //Log.d("Inserted","ID -> " + ID);
        return;
    }
    public Content getContent(long id,String day){
        //select *  from database where id=1
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[]{KEY_ID,KEY_TITLE,KEY_ACTIVITY,KEY_PRIORITY,KEY_TIME_START,KEY_TIME_END,KEY_DESCRIPTION,KEY_PERMANENT,KEY_EVENT,KEY_ADDRESS,KEY_NOTIFICATION_ID};
        String[] query2 = new String[]{KEY_ID,KEY_TITLE,KEY_ACTIVITY,KEY_TIME_START,KEY_TIME_END,KEY_DATE,KEY_DESCRIPTION,KEY_PERMANENT,KEY_EVENT,KEY_ADDRESS,KEY_NOTIFICATION_ID};
        Cursor cursor;
        switch(day){
            case "MONDAY":
                cursor =db.query(MONDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "TUESDAY":
                cursor =db.query(TUESDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "WEDNESDAY":
                cursor =db.query(WEDNESDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "THURSDAY":
                cursor =db.query(THURSDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "FRIDAY":
                cursor =db.query(FRIDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "SATURDAY":
                cursor =db.query(SATURDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "SUNDAY":
                cursor =db.query(SUNDAY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "TEMPORARY":
                cursor =db.query(TEMPORARY_TABLE,query,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;
            case "EVENT":
                cursor =db.query(EVENT_TABLE,query2,KEY_ID+"=?",
                        new String[] {String.valueOf(id)},null,null,null,null);
                break;

            default:
                cursor=null;
        }

        Log.i("DAY", "Name  " + day);
        if(cursor !=null) cursor.moveToFirst();
        Content content;
        if(day.equals("EVENT")){
            content=new Content(
                    Long.parseLong(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    Integer.parseInt( cursor.getString(7))>0,
                    Integer.parseInt( cursor.getString(8))>0,
                    cursor.getString(9),
                    Integer.parseInt(cursor.getString(10)));
        }
        else {
            content=new Content(
                    Long.parseLong(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    Integer.parseInt( cursor.getString(7))>0,
                    Integer.parseInt( cursor.getString(8))>0,
                    cursor.getString(9),
                    Integer.parseInt(cursor.getString(10)));
        }
        db.close();
        return content;
    }

    public ArrayList<Content> getContents(String day){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Content> allContent = new ArrayList<>();
        //selected * from database
        Log.i("DAY", "Name  " + day);
        String query;
        switch(day){
            case "MONDAY":
                query = "SELECT * FROM " + MONDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "TUESDAY":
                query = "SELECT * FROM " + TUESDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "WEDNESDAY":
                query = "SELECT * FROM " + WEDNESDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "THURSDAY":
                query = "SELECT * FROM " + THURSDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "FRIDAY":
                query = "SELECT * FROM " + FRIDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "SATURDAY":
                query = "SELECT * FROM " + SATURDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "SUNDAY":
                query = "SELECT * FROM " + SUNDAY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "TEMPORARY":
                query = "SELECT * FROM " + TEMPORARY_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            case "EVENT":
                query = "SELECT * FROM " + EVENT_TABLE+" ORDER BY "+KEY_ID+" DESC";
                break;
            default:
                query="";

        }
        Log.i("Query", "Name  " + query);
        Cursor cursor =db.rawQuery(query,null);
        if(day.equals("EVENT")){
            if(cursor.moveToFirst()){
                do {
                    Content content = new Content();
                    content.setID(Long.parseLong(cursor.getString(0)));
                    content.setTitle(cursor.getString(1));
                    content.setActivity(Integer.parseInt(cursor.getString(2)));
                    content.setTimeStart(cursor.getString(3));
                    content.setTimeEnd(cursor.getString(4));
                    content.setDate(cursor.getString(5));
                    content.setDescription(cursor.getString(6));
                    content.setPermanent(Integer.parseInt( cursor.getString(7))>0);
                    content.setEvent(Integer.parseInt( cursor.getString(8))>0);
                    content.setAddress(cursor.getString(9));
                    content.setNotificationID(Integer.parseInt(cursor.getString(10)));
                    allContent.add(content);
                }while(cursor.moveToNext());
            }
        }
        else{
            if(cursor.moveToFirst()){
                do {
                    Content content = new Content();
                    content.setID(Long.parseLong(cursor.getString(0)));
                    content.setTitle(cursor.getString(1));
                    content.setActivity(Integer.parseInt(cursor.getString(2)));
                    content.setPriority(Integer.parseInt(cursor.getString(3)));
                    content.setTimeStart(cursor.getString(4));
                    content.setTimeEnd(cursor.getString(5));
                    content.setDescription(cursor.getString(6));
                    content.setPermanent(Integer.parseInt( cursor.getString(7))>0);
                    content.setEvent(Integer.parseInt( cursor.getString(8))>0);
                    content.setAddress(cursor.getString(9));
                    content.setNotificationID(Integer.parseInt(cursor.getString(10)));
                    allContent.add(content);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return allContent;
    }

    public void editContent(Content content, String day){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(KEY_TITLE,content.getTitle());
        c.put(KEY_ACTIVITY,content.getActivity());
        c.put(KEY_PRIORITY,content.getPriority());
        c.put(KEY_TIME_START,content.getTimeStart());
        c.put(KEY_TIME_END,content.getTimeEnd());
        c.put(KEY_DESCRIPTION,content.getDescription());
        c.put(KEY_PERMANENT,content.isPermanent());
        c.put(KEY_EVENT,content.isEvent());
        c.put(KEY_ADDRESS,content.getAddress());
        c.put(KEY_NOTIFICATION_ID,content.getNotificationID());

        ContentValues c2 = new ContentValues();
        c2.put(KEY_TITLE,content.getTitle());
        c2.put(KEY_ACTIVITY,content.getActivity());
        c2.put(KEY_TIME_START,content.getTimeStart());
        c2.put(KEY_TIME_END,content.getTimeEnd());
        c2.put(KEY_DATE,content.getDate());
        c2.put(KEY_DESCRIPTION,content.getDescription());
        c2.put(KEY_PERMANENT,content.isPermanent());
        c2.put(KEY_EVENT,content.isEvent());
        c2.put(KEY_ADDRESS,content.getAddress());
        c2.put(KEY_NOTIFICATION_ID,content.getNotificationID());

        switch(day){
            case "MONDAY":
                db.update(MONDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "TUESDAY":
                db.update(TUESDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "WEDNESDAY":
                db.update(WEDNESDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "THURSDAY":
                db.update(THURSDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "FRIDAY":
                db.update(FRIDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "SATURDAY":
                db.update(SATURDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "SUNDAY":
                db.update(SUNDAY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "TEMPORARY":
                db.update(TEMPORARY_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;
            case "EVENT":
                db.update(EVENT_TABLE,c2,KEY_ID+"=?",new String[]{String.valueOf(content.getID())});
                db.close();
                break;

        }
    }
    public boolean searchContent(Content content, String day){
        ArrayList<Content>  contentFind = getContents(day);
        for(Content content1:contentFind){
            if(content1.equals(content)){
                return true;
            }
        }
        return false;
    }
    public void deleteContent(long id,String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        switch(day){
            case "MONDAY":
                db.delete(MONDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "TUESDAY":
                db.delete(TUESDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "WEDNESDAY":
                db.delete(WEDNESDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "THURSDAY":
                db.delete(THURSDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "FRIDAY":
                db.delete(FRIDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "SATURDAY":
                db.delete(SATURDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "SUNDAY":
                db.delete(SUNDAY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "TEMPORARY":
                db.delete(TEMPORARY_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
            case "EVENT":
                db.delete(EVENT_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
                break;
        }
        db.close();
    }
    public void deleteAllContent(String day){
        SQLiteDatabase db = this.getWritableDatabase();
        switch(day){
            case "MONDAY":
                db.delete(MONDAY_TABLE,null,null);
                break;
            case "TUESDAY":
                db.delete(TUESDAY_TABLE,null,null);
                break;
            case "WEDNESDAY":
                db.delete(WEDNESDAY_TABLE,null,null);
                break;
            case "THURSDAY":
                db.delete(THURSDAY_TABLE,null,null);
                break;
            case "FRIDAY":
                db.delete(FRIDAY_TABLE,null,null);
                break;
            case "SATURDAY":
                db.delete(SATURDAY_TABLE,null,null);
                break;
            case "SUNDAY":
                db.delete(SUNDAY_TABLE,null,null);
                break;
            case "TEMPORARY":
                db.delete(TEMPORARY_TABLE,null,null);
                break;
            case "EVENT":
                db.delete(EVENT_TABLE,null,null);
                break;
        }
        db.close();
    }

}
