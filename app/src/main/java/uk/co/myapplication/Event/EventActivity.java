package uk.co.myapplication.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.myapplication.Adapter.EventAdapter;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.MainActivity;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class EventActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener, EventAdapter.OnEventListener {
    RecyclerView recyclerView;
    public static ArrayList<Content> contents;
    private static ContentDatabase database;
    private Comparator<Content> byDate;
    EventAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton addContentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        toolbar = (Toolbar) findViewById(R.id.eventToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EVENTS");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        Log.d("Clickable","Not so far");
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventActivity.this, MainActivity.class));
            }
        });

        database = new ContentDatabase(this);
        contents = database.getContents("EVENT");
        if(!contents.isEmpty()){
            System.out.println("isPermanent?Really:"+contents.get(0).isPermanent());
        }
        setComparator();
        Collections.sort(contents, byDate);
        recyclerView = findViewById(R.id.eventRecyclerView);

        addContentBtn = findViewById(R.id.AddEventBtn);
        addContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventActivity.this, AddEvent.class));
            }
        });
        setRecyclerView();
    }
    private void setComparator() {
        byDate = new Comparator<Content>() {
            public int compare(Content c1, Content c2) {
                try {
                    return convertStringToDate(c1.getDate()).compareTo(convertStringToDate(c2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("Didn't work");
                }
                return 0;
            }
        };
    }
    private Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yy").parse(date);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        adapter = new EventAdapter(this, contents, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void deleteEvent(Content content) {
        contents.remove(content);
        adapter.notifyDataSetChanged();
        long id = content.getID();
        database.deleteContent(id, "EVENT");
    }

    private void editContent(Content content) {
        long id = content.getID();
        Intent intent = new Intent(EventActivity.this, EditEvent.class);
        intent.putExtra("Content", id);
        intent.putExtra("DAY", "EVENT");
        startActivity(intent);
    }

    private void showEvent(Content content){
        long id=content.getID();
        Intent intent = new Intent(EventActivity.this, ShowEvent.class);
        intent.putExtra("Content", id);
        intent.putExtra("DAY", "EVENT");
        startActivity(intent);
    }
    @Override
    public void onClick(View v) { }

    @Override
    public void onEventClick(int position) { showEvent(contents.get(position));}

    @Override
    public void onEditClick(int position) {
        editContent(contents.get(position));
    }

    @Override
    public void onDeleteClick(int position) { deleteEvent(contents.get(position)); }

    @Override
    public void onShareClick(int position) {

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteEvent(contents.get(viewHolder.getAdapterPosition()));


        }
    };
}
