package uk.co.myapplication.ScheduleTasks;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.myapplication.MainActivity;
import uk.co.myapplication.R;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopUpAlarm extends AppCompatActivity {
    Button dismissBtn;
    TextView titleText,timeText;
    Ringtone ringTone;
    Uri uriAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_alarm);

        titleText=findViewById(R.id.TitlePopUp);
        timeText=findViewById(R.id.timePopUp);

        String title=getIntent().getExtras().getString("Title");
        String time=getIntent().getExtras().getString("Time");

        titleText.setText(title);
        timeText.setText(time);

        uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringTone = RingtoneManager
                .getRingtone(getApplicationContext(), uriAlarm);
        ringTone.play();
        Log.d("MyAlarmSystem", "-> 4.Start");

        dismissBtn=findViewById(R.id.DismissBtn);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyAlarmSystem", "-> 5.Stop");
                ringTone.stop();
                startActivity(new Intent(PopUpAlarm.this, MainActivity.class));
            }
        });
    }
}
