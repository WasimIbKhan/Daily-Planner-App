package uk.co.myapplication.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditEvent extends AppCompatActivity {
    Toolbar toolbar;
    EditText eTitleEditText,eDescriptionEditText,eAddressEditText;
    Content content;
    TimePicker eSimpleTimePickerStart,eSimpleTimePickerEnd;
    DatePicker eDatePicker;
    String eContentTitle,eContentTimeStart="",eContentTimeEnd="",eContentDate="",eContentDescription="",eContentAddress="";
    private int eContentActivity;
    RadioGroup radioGroup;
    String hour, minute,day,month,year;
    ContentDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        toolbar = (Toolbar)findViewById(R.id.eEventToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Event");

        db = new ContentDatabase(this);

        eTitleEditText=findViewById(R.id.eEventTitle);
        eDescriptionEditText=findViewById(R.id.eEventDescription);
        eAddressEditText=findViewById(R.id.eAddress);
        eSimpleTimePickerStart = (TimePicker) findViewById(R.id.eEventSimpleTimePickerStart); // initiate a time picker
        eSimpleTimePickerStart.setIs24HourView(true); // set 24 hours mode for the time picker
        eSimpleTimePickerEnd = (TimePicker) findViewById(R.id.eEventSimpleTimePickerEnd); // initiate a time picker
        eSimpleTimePickerEnd.setIs24HourView(true); // set 24 hours mode for the time picker
        eDatePicker =(DatePicker) findViewById(R.id.eEventDatePicker);


        radioGroup=findViewById(R.id.eEventRadioGroup);

        if(getIntent().hasExtra("Content")){
            long id= getIntent().getExtras().getLong("Content");
            String day =getIntent().getExtras().getString("DAY");
            content=db.getContent(id,day);
            eTitleEditText.setText(content.getTitle());
            eDescriptionEditText.setText(content.getDescription());
            eAddressEditText.setText(content.getAddress());


        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_content,menu);
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
            if(eTitleEditText.getText().length() != 0) {
                if(timeStart.compareTo(timeEnd)<0) {
                eContentTitle = eTitleEditText.getText().toString();
                eContentActivity = getActivity();
                eContentTimeStart = getTimeStart();
                eContentTimeEnd=getTimeEnd();
                eContentDate = getDate();
                eContentDescription = eDescriptionEditText.getText().toString();
                eContentAddress=eAddressEditText.getText().toString();
                content.setTitle(eContentTitle);
                content.setActivity(eContentActivity);
                content.setTimeStart(eContentTimeStart);
                content.setTimeEnd(eContentTimeEnd);
                content.setDate(eContentDate);
                content.setDescription(eContentDescription);
                content.setAddress(eContentAddress);
                Log.d("Edited", "Edited Title: -> " + content.getTitle() + "\n ID -> " + content.getID());
                db.editContent(content, getIntent().getExtras().getString("DAY"));
                Toast.makeText(this, "Content Edited", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EventActivity.class));
                }else {
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
    public void onBackPressed(){
        super.onBackPressed();
    }

    private String getDate() {
        day =pad(eDatePicker.getDayOfMonth());
        month =pad(eDatePicker.getMonth()+1);
        year= pad(eDatePicker.getYear());

        return day +"/" +month +"/"+year;
    }

    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("kk:mm").parse(time);
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

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
    private int getActivity() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.eCorporateCheckbox) { return 0; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.ePrivateCheckbox) { return 1; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.ePartyCheckbox) { return 2; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.eCelebrationCheckbox) { return 3; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.eOtherCheckbox) { return 4; }
        return 0;
    }
}
