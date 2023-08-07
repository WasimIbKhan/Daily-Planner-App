package uk.co.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.widget.Toolbar;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.ScheduleTasks.AddScheduleTask;

public class AddContent extends AppCompatActivity {
    Toolbar toolbar;
    EditText titleEditText,descriptionEditText;
    Content content;
    TimePicker simpleTimePickerStart,simpleTimePickerEnd;
    String contentTitle,contentTimeStart="",contentTimeEnd="",contentDescription="";
    int contentPriority,contentActivity ;
    boolean isPermenant=true;
    RadioGroup priorityRadioGroup,activityRadioGroup;
    String hourStart,hourEnd, minuteStart,minuteEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        toolbar = (Toolbar)findViewById(R.id.addToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Schedule");

        titleEditText=findViewById(R.id.addTitle);
        descriptionEditText=findViewById(R.id.addDescription);
        simpleTimePickerStart = (TimePicker) findViewById(R.id.simpleTimePickerStart); // initiate a time picker
        simpleTimePickerStart.setIs24HourView(true); // set 24 hours mode for the time picker

        simpleTimePickerEnd= (TimePicker) findViewById(R.id.simpleTimePickerEnd); // initiate a time picker
        simpleTimePickerEnd.setIs24HourView(true); // set 24 hours mode for the time picker

        priorityRadioGroup=(RadioGroup) findViewById(R.id.priority_radioGroup);
        activityRadioGroup=(RadioGroup) findViewById(R.id.activity_radioGroup);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_content,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.save){
            Date timeStart=null;
            Date timeEnd=null;
            try {
                timeStart=convertStringToTime(getTimeStart());
                timeEnd=convertStringToTime(getTimeEnd());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("TimePicker",""+timeStart);
            if(titleEditText.getText().length() != 0) {
                 if(timeStart.compareTo(timeEnd)<0){
                     String day=getIntent().getExtras().getString("DAY");
                    contentTitle = titleEditText.getText().toString();
                    contentTimeStart=getTimeStart();
                    contentTimeEnd=getTimeEnd();
                    contentPriority=getPriority();
                    contentActivity=getActivityType();
                    contentDescription = descriptionEditText.getText().toString();
                    Log.d("Time addContentActivity",contentTimeEnd);
                    isPermenant=getIntent().getExtras().getBoolean("isPermanent");
                    content = new Content(contentTitle,contentActivity,contentPriority,contentTimeStart,contentTimeEnd,contentDescription,isPermenant,false,"",0);
                    ContentDatabase db = new ContentDatabase(this);
                    AddScheduleTask addScheduleTask= new AddScheduleTask(this,content,day);
                    addScheduleTask.addNotification();
                    content.setNotificationID(addScheduleTask.getNotificationID());
                    db.addContent(content,day);
                    Toast.makeText(this, "Content Added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else {
                    Toast.makeText(this,"End Time Must be After Start Time", Toast.LENGTH_SHORT).show();
                }

            }else {
                titleEditText.setError("Title cannot be blank");
            }
        }
        if(item.getItemId() == R.id.delete){
            Toast.makeText(this,"Deleted", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private int getPriority() {
        if (priorityRadioGroup.getCheckedRadioButtonId() == R.id.add_low_priority_checkbox)
        {
            return 0;
        }
        if (priorityRadioGroup.getCheckedRadioButtonId() == R.id.add_medium_priority_checkbox)
        {
            return 1;
        }
        if (priorityRadioGroup.getCheckedRadioButtonId() == R.id.add_high_priority_checkbox)
        {
            return 2;
        }
        if (priorityRadioGroup.getCheckedRadioButtonId() == R.id.add_top_priority_checkbox)
        {
            return 3;
        }
        return 0;
    }

    public int getActivityType(){
        if (activityRadioGroup.getCheckedRadioButtonId() == R.id.workCheckbox)
        {
            return 0;
        }
        if (activityRadioGroup.getCheckedRadioButtonId() == R.id.socialCheckbox)
        {
            return 1;
        }
        if (activityRadioGroup.getCheckedRadioButtonId() == R.id.learningCheckbox)
        {
            return 2;
        }
        if (activityRadioGroup.getCheckedRadioButtonId() == R.id.leisureCheckbox)
        {
            return 3;
        }
        if (activityRadioGroup.getCheckedRadioButtonId() == R.id.breaksCheckbox)
        {
            return 4;
        }
        return 0;
    }
    public void onBackPressed(){
        super.onBackPressed();
    }

    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("hh:mm").parse(time);
    }
    public String getTimeStart(){
        hourStart =pad(simpleTimePickerStart.getCurrentHour());
        minuteStart = pad(simpleTimePickerStart.getCurrentMinute());

        return hourStart+":"+minuteStart;
    }
    private String getTimeEnd() {
        hourEnd =pad(simpleTimePickerEnd.getCurrentHour());
        minuteEnd = pad(simpleTimePickerEnd.getCurrentMinute());
        Log.d("TimePicker",hourEnd+":"+minuteEnd);
        return hourEnd+":"+minuteEnd;
    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }



}
