package uk.co.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
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
import uk.co.myapplication.ScheduleTasks.EditNotificationSchedule;


public class EditContent extends AppCompatActivity {
    Toolbar toolbar;
    EditText eTitleEditText,eDescriptionEditText;
    Content content;
    TimePicker eSimpleTimePickerStart,eSimpleTimePickerEnd;
    String eContentTitle,econtentTimeStart="",eContentTimeEnd="",eContentDescription="",day;
    private int eContentPriority,eContentActivity;
    RadioGroup radioGroup,activityRadioGroup;
    RadioButton eLow_priority_checkbox,eMedium_priority_checkbox,eHigh_priority_checkbox,eTop_priority_checkbox;
    String hour, minute;
    ContentDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);

        toolbar = (Toolbar)findViewById(R.id.eToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Schedule");

        db = new ContentDatabase(this);

        eTitleEditText=findViewById(R.id.eTitle);
        eDescriptionEditText=findViewById(R.id.EditDescription);

        eSimpleTimePickerStart = (TimePicker) findViewById(R.id.eSimpleTimePickerStart); // initiate a time picker
        eSimpleTimePickerStart.setIs24HourView(true); // set 24 hours mode for the time picker

        eSimpleTimePickerEnd= (TimePicker) findViewById(R.id.eSimpleTimePickerEnd); // initiate a time picker
        eSimpleTimePickerEnd.setIs24HourView(true); // set 24 hours mode for the time picker

        if(getIntent().hasExtra("Content")){
            long id= getIntent().getExtras().getLong("Content");
            day =getIntent().getExtras().getString("DAY");
            content=db.getContent(id,day);
            eTitleEditText.setText(content.getTitle());
            eDescriptionEditText.setText(content.getDescription());
        }

        radioGroup=(RadioGroup) findViewById(R.id.ePriorityRadioGroup);
        eLow_priority_checkbox=findViewById(R.id.edit_low_priority_checkbox);
        eMedium_priority_checkbox=findViewById(R.id.edit_medium_priority_checkbox);
        eHigh_priority_checkbox=findViewById(R.id.edit_high_priority_checkbox);
        eTop_priority_checkbox=findViewById(R.id.edit_top_priority_checkbox);

        activityRadioGroup=(RadioGroup) findViewById(R.id.edit_activity_radioGroup);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_content,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.save){
            if(eTitleEditText.getText().length() != 0) {
                if(timeStart(getTimeStart()).before(timeEnd(getTimeEnd()))) {
                    eContentTitle = eTitleEditText.getText().toString();
                    eContentTimeEnd=getTimeEnd();
                    econtentTimeStart = getTimeStart();
                    eContentPriority = getPriority();
                    eContentActivity = getActivityType();
                    eContentDescription = eDescriptionEditText.getText().toString();
                    content.setTitle(eContentTitle);
                    content.setPriority(eContentPriority);
                    content.setActivity(eContentActivity);
                    content.setTimeStart(econtentTimeStart);
                    content.setTimeEnd(eContentTimeEnd);
                    content.setDescription(eContentDescription);
                    EditNotificationSchedule editScheduleTask= new EditNotificationSchedule(this,content,day);
                    editScheduleTask.editNotification();
                    Log.d("Edited", "Edited Title: -> " + content.getTitle() + "\n ID -> " + content.getID());
                    db.editContent(content, getIntent().getExtras().getString("DAY"));
                    Toast.makeText(this, "Content Edited", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    Toast.makeText(this,"End Time Must be After Start Time", Toast.LENGTH_SHORT).show();
                }

            }else {
                eTitleEditText.setError("Title cannot be blank");
            }
        }
        if(item.getItemId() == R.id.delete){
            Toast.makeText(this,"Deleted", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private int getActivityType() {
        switch (activityRadioGroup.getCheckedRadioButtonId()){
            case R.id.eWorkCheckbox:
                return 0;
            case R.id.eSocialCheckbox:
                return 1;
            case R.id.eLearningCheckbox:
                return 2;
            case R.id.eLeisureCheckbox:
                return 3;
            case R.id.eBreaksCheckbox:
                return 4;
            default:
                return 0;
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("hh:mm").parse(time);
    }

    public String getTimeStart(){
        hour =pad(eSimpleTimePickerStart.getCurrentHour());
        minute = pad(eSimpleTimePickerStart.getCurrentMinute());

        return hour+":"+minute;
    }
    private String getTimeEnd() {
        hour =pad(eSimpleTimePickerEnd.getCurrentHour());
        minute = pad(eSimpleTimePickerEnd.getCurrentMinute());

        return hour+":"+minute;
    }

    private Date timeStart(String time){
        try {
            return convertStringToTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Date timeEnd(String time){
        try {
            return convertStringToTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }

    private int getPriority() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.edit_low_priority_checkbox)
        {
            return 0;
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.edit_medium_priority_checkbox)
        {
            return 1;
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.edit_high_priority_checkbox)
        {
            return 2;
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.edit_top_priority_checkbox)
        {
            return 3;
        }
        return 0;
    }
}
