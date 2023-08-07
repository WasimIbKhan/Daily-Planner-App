package uk.co.myapplication.Event;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;

import android.os.Bundle;
import android.widget.TextView;

public class ShowEvent extends AppCompatActivity {

    TextView titleText,dateText,timeText,descriptionText,addressText;
    Content content;
    ContentDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        db = new ContentDatabase(this);

        titleText = findViewById(R.id.DisplayTitleText);
        dateText= findViewById(R.id.DisplayDateText);
        timeText=findViewById(R.id.DisplayTimeText);
        descriptionText =findViewById(R.id.DisplayDescriptionText);
        addressText=findViewById(R.id.AddressText);

        if(getIntent().hasExtra("Content")){
            long id= getIntent().getExtras().getLong("Content");
            String day =getIntent().getExtras().getString("DAY");
            content=db.getContent(id,day);
            titleText.setText(content.getTitle());
            dateText.setText(content.getDate());
            timeText.setText(content.getTimeStart());
            addressText.setText(content.getAddress());
            descriptionText.setText(content.getDescription());
        }

    }
}
