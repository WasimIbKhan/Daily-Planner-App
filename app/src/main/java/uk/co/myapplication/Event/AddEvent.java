package uk.co.myapplication.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.MainActivity;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AddEvent extends AppCompatActivity {

    Toolbar toolbar;
    EditText titleEditText,descriptionEditText,addressEditText,websiteEditText;
    Content content;
    TimePicker simpleTimePickerStart,simpleTimePickerEnd;
    DatePicker datePicker;
    String contentTitle,contentTimeStart="",contentTimeEnd="",contentDate="",contentDescription="",contentAddress;
    private int contentActivity;
    RadioGroup radioGroup;
    String hour, minute,day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        toolbar = (Toolbar) findViewById(R.id.addEventToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Event");

        titleEditText=findViewById(R.id.AddEventTitle);
        descriptionEditText=findViewById(R.id.AddEventDescription);
        addressEditText=findViewById(R.id.addAddress);
        simpleTimePickerStart = (TimePicker) findViewById(R.id.AddEventSimpleTimePickerStart); // initiate a time picker
        simpleTimePickerStart.setIs24HourView(true); // set 24 hours mode for the time picker
        simpleTimePickerEnd = (TimePicker) findViewById(R.id.AddEventSimpleTimePickerEnd); // initiate a time picker
        simpleTimePickerEnd.setIs24HourView(true); // set 24 hours mode for the time picker
        datePicker =(DatePicker) findViewById(R.id.AddEventDatePicker);

        radioGroup=findViewById(R.id.eventRadioGroup);

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
            if(titleEditText.getText().length() != 0) {
                if(timeStart.compareTo(timeEnd)<0) {
                    contentTitle = titleEditText.getText().toString();
                    contentActivity = getActivity();
                    contentTimeStart = getTimeStart();
                    contentTimeEnd=getTimeEnd();
                    contentDate = getDate();
                    contentDescription = descriptionEditText.getText().toString();
                    contentAddress = addressEditText.getText().toString();
                    content = new Content(contentTitle, contentActivity, contentTimeStart, contentTimeEnd, contentDate, contentDescription, true, true, contentAddress,0);
                    ContentDatabase db = new ContentDatabase(this);
                    db.addContent(content, "EVENT");
                    Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, EventActivity.class));
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

    private int getActivity() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.CorporateCheckbox) { return 0; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.PrivateCheckbox) { return 1; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.PartyCheckbox) { return 2; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.CelebrationCheckbox) { return 3; }
        if (radioGroup.getCheckedRadioButtonId() == R.id.OtherCheckbox) { return 4; }
        return 0;
    }

    private String getDate() {
        day =pad(datePicker.getDayOfMonth());
        month =pad(datePicker.getMonth()+1);
        year= pad(datePicker.getYear());
        System.out.println(day +"/" +month +"/"+year);
        return day +"/" +month +"/"+year;
    }


    public void onBackPressed(){
        super.onBackPressed();
    }

    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("hh:mm").parse(time);
    }

    public String getTimeStart(){
        hour =pad(simpleTimePickerStart.getCurrentHour());
        minute = pad(simpleTimePickerStart.getCurrentMinute());

        return hour+":"+minute;
    }
    private String getTimeEnd() {
        hour =pad(simpleTimePickerEnd.getCurrentHour());
        minute = pad(simpleTimePickerEnd.getCurrentMinute());

        return hour+":"+minute;
    }


    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
}
