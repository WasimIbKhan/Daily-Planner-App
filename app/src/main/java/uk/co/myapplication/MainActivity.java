package uk.co.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.Event.EventActivity;
import uk.co.myapplication.Model.Dates;
import uk.co.myapplication.ScheduleTasks.ScheduleTasks;
import uk.co.myapplication.ui.main.SectionsPagerAdapter;
import uk.co.myapplication.ui.main.SwipeDisabledViewPager;

public class MainActivity extends AppCompatActivity {
    public ContentDatabase database = new ContentDatabase(this);
    Toolbar toolbar;
    private SwipeDisabledViewPager viewPager;
    private TabLayout tabs;
    Dates dates = new Dates();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.TitleToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedule");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = (SwipeDisabledViewPager)findViewById(R.id.view_pager);
        viewPager.setPagingEnabled(true);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs  = findViewById(R.id.tabs);
        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.setupWithViewPager(viewPager);
            }
        });
        switch (dates.getToday()){
            case "MONDAY":
                selectTabIndex(0);
            case "TUESDAY":
                selectTabIndex(1);
            case "WEDNESDAY":
                selectTabIndex(2);
            case "THURSDAY":
                selectTabIndex(3);
            case "FRIDAY":
                selectTabIndex(4);
            case "SATURDAY":
                selectTabIndex(5);
            case "SUNDAY":
                selectTabIndex(6);
            default:
                selectTabIndex(0);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));


        ScheduleTasks scheduleTasks = new ScheduleTasks(this);
        scheduleTasks.ScheduleDailyTasks();
        scheduleTasks.ScheduleEventTasks();
        scheduleTasks.ScheduleCancel();
    }

    private void selectTabIndex(final int index){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tabs.setScrollPosition(index, 0, true);
                viewPager.setCurrentItem(index);
                // or
                // tabLayout.getTabAt(index).select();
            }
        },100);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.Event){
            startActivity(new Intent(MainActivity.this, EventActivity.class));
        }
        if(item.getItemId() == R.id.delete){

        }
        return super.onOptionsItemSelected(item);
    }
}







