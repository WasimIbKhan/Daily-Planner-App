package uk.co.myapplication.DailyActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.myapplication.Adapter.ContentAdapter;
import uk.co.myapplication.AddContent;
import uk.co.myapplication.Databases.ContentDatabase;
import uk.co.myapplication.EditContent;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.Model.Dates;
import uk.co.myapplication.R;
import uk.co.myapplication.ScheduleTasks.AddScheduleTask;
import uk.co.myapplication.ScheduleTasks.CancelTask;
import uk.co.myapplication.ScheduleTasks.ScheduleDailyTasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Wednesday extends Fragment implements FloatingActionButton.OnClickListener, ContentAdapter.OnContentListener {

    private RecyclerView recyclerView;
    public static ArrayList<Content> contents,temporaryContents,eventContents;
    private static ContentDatabase database;
    private ContentAdapter adapter;
    private Comparator<Content> byTime;
    private RadioGroup switchButton;
    private RadioButton permanentSwitch,temporarySwitch;
    private static boolean isPermanent=true;
    private static boolean addPressed=false;
    Dates dates =new Dates();
    Dialog epicDialog;
    Button completePopupBtn;
    ImageButton closePopupBtn;
    public Wednesday() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = (ViewGroup) inflater.inflate(R.layout.fragment_wednesday, container, false);
        database = new ContentDatabase(getActivity());
        setComparator();
        contents= database.getContents("WEDNESDAY");
        addEvents();
        Collections.sort(contents, byTime);
        FloatingActionButton addPermanentContentBtn = root.findViewById(R.id.addPermanentContentWednesday);
        FloatingActionButton addTemporaryContentBtn=root.findViewById(R.id.addTemporaryContentWednesday);
        addPermanentContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPressed=true;
                Intent intent = new Intent(getActivity(), AddContent.class);
                intent.putExtra("DAY", "WEDNESDAY");
                intent.putExtra("isPermanent",true);
                startActivity(intent);
            }
        });
        addTemporaryContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPressed=true;
                Intent intent = new Intent(getActivity(), AddContent.class);
                intent.putExtra("DAY", "WEDNESDAY");
                intent.putExtra("isPermanent",false);
                startActivity(intent);
            }
        });
        switchButton=root.findViewById(R.id.toggleWednesday);
        recyclerView=root.findViewById(R.id.WednesdayRecyclerView);
        setRecyclerView(contents);
        permanentSwitch=root.findViewById(R.id.permanentSwitch);
        temporarySwitch=root.findViewById(R.id.temporarySwitch);
        switchButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId ==R.id.permanentSwitch){
                    isPermanent=true;
                    temporarySwitch.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                    permanentSwitch.setTextColor(Color.parseColor("#FFFFFF"));
                    contents=database.getContents("WEDNESDAY");
                    Collections.sort(contents, byTime);
                    setRecyclerView(contents);
                }
                else if (checkedId == R.id.temporarySwitch) {
                    createTemporary();
                    isPermanent=false;
                    permanentSwitch.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                    temporarySwitch.setTextColor(Color.parseColor("#FFFFFF"));
                    temporaryContents=database.getContents("TEMPORARY");
                    Collections.sort(temporaryContents, byTime);
                    setRecyclerView(temporaryContents);
                }
            }
        });
        if(isPermanent){
            switchButton.check(R.id.permanentSwitch);
        }
        else{
            switchButton.check(R.id.temporarySwitch);
        }
        return root;
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        if(addPressed){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            addPressed=false;
        }
    }
    private void setRecyclerView(ArrayList<Content> contents) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView );
        adapter = new ContentAdapter(getActivity(), contents,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
    }

    private void createTemporary() {
        database.deleteAllContent("TEMPORARY");
        for(Content content:contents){
            if(!content.isPermanent()){
                database.addContent(content,"TEMPORARY");
            }
        }
    }

    private void addEvents() {
        boolean isPresent=false;
        eventContents= database.getContents("EVENT");
        if(!contents.isEmpty()) {
            for (int i = 0; i < contents.size(); i++) {
                if(!eventContents.isEmpty()){
                    for (int j = 0; j < eventContents.size(); j++) {
                        if (contents.get(i).getTitle().equals(eventContents.get(j).getTitle()) && (contents.get(i).isEvent() && (eventContents.get(j).isEvent()))) {
                            isPresent=true;
                        }
                    }
                }
            }
        }
        for(Content content:eventContents){
            try {
                if(convertStringToDate(dates.getWednesday()).compareTo(convertStringToDate(content.getDate()))==0){
                    if(!isPresent){
                        AddScheduleTask addScheduleTask= new AddScheduleTask(getActivity(),content,"WEDNESDAY");
                        addScheduleTask.addNotification();
                        content.setNotificationID(addScheduleTask.getNotificationID());
                        database.addContent(content, "WEDNESDAY");
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setComparator() {
        byTime = new Comparator<Content>() {
            public int compare(Content c1, Content c2) {
                try {
                    return Long.valueOf(convertStringToTime(c1.getTimeStart() +":00").getTime()).compareTo(convertStringToTime(c2.getTimeStart()  +":00").getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("Didn't work");
                }
                return 0;
            }
        };
    }

    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("hh:mm:ss").parse(time);
    }
    public Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yy").parse(date);
    }
    private void deleteContent(Content content) {
        if(dates.getWednesday().equals(dates.getToday())){
            CancelTask cancelTask = new CancelTask(getActivity(),content);
            cancelTask.deleteScheduledNotification();
        }
        contents.remove(content);
        if(temporaryContents!=null){
            if(containsContent(temporaryContents,content))
                temporaryContents.remove(content);
        }
        adapter.notifyDataSetChanged();
        long id=content.getID();
        database.deleteContent(id,"WEDNESDAY");
    }

    private boolean containsContent(ArrayList<Content> Contents, Content searchContent) {
        for(Content content:Contents){
            if(searchContent.equals(content)){
                return true;
            }
        }
        return false;
    }

    private void editContent(Content content){
        long id=content.getID();
        Intent intent = new Intent(getActivity(), EditContent.class);
        intent.putExtra("Content", id);
        intent.putExtra("DAY", "WEDNESDAY");
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(),AddContent.class));

    }
    @Override
    public void onContentClick(int position) {
    }

    @Override
    public void onEditClick(int position) {
        if(isPermanent){
            editContent(contents.get(position));
        }
        if(!isPermanent){
            Content temporaryContent=temporaryContents.get(position);
            for(Content content:contents){
                if(content.getTitle().equals(temporaryContent.getTitle()) && content.getTimeStart().equals(temporaryContent.getTimeStart()) && content.getPriority()==(temporaryContent.getPriority()) && (!content.isPermanent() && !temporaryContent.isPermanent())) {
                    editContent(content);
                }
            }
        }
    }


    @Override
    public void onMoreClick(int position, String choice) {
        epicDialog= new Dialog(getActivity());
        switch(choice){
            case "Move":
                epicDialog.setContentView(R.layout.pop_up_move);
                moveDialog(epicDialog,position);
            case "Duplicate":
                epicDialog.setContentView(R.layout.pop_up_duplicate);
                duplicateDialog(epicDialog,position);
            case "Skip":
                skipNotification(position);
        }


    }

    private void skipNotification(int position) {
        Content content =contents.get(position);
        if(dates.getWednesday().equals(dates.getToday())){
            CancelTask cancelTask = new CancelTask(getActivity(),content);
            cancelTask.deleteScheduledNotification();
        }
    }

    private void duplicateDialog(final Dialog epicDialog, final int position) {
        final CheckBox mondayCB=(CheckBox) epicDialog.findViewById(R.id.MondayCB);
        final CheckBox tuesdayCB=(CheckBox) epicDialog.findViewById(R.id.TuesdayCB);
        final CheckBox wednesdayCB=(CheckBox) epicDialog.findViewById(R.id.WednesdayCB);
        final CheckBox thursdayCB=(CheckBox) epicDialog.findViewById(R.id.ThursdayCB);
        final CheckBox fridayCB=(CheckBox) epicDialog.findViewById(R.id.FridayCB);
        final CheckBox saturdayCB=(CheckBox) epicDialog.findViewById(R.id.SaturdayCB);
        final CheckBox sundayCB=(CheckBox) epicDialog.findViewById(R.id.SundayCB);

        completePopupBtn=(Button) epicDialog.findViewById(R.id.CompleteBtn);
        completePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mondayCB.isChecked()){
                    database.addContent(contents.get(position),"MONDAY");
                }
                if(tuesdayCB.isChecked()){
                    database.addContent(contents.get(position),"TUESDAY");
                }
                if(wednesdayCB.isChecked()){
                    database.addContent(contents.get(position),"WEDNESDAY");
                }
                if(thursdayCB.isChecked()){
                    database.addContent(contents.get(position),"THURSDAY");
                }
                if(fridayCB.isChecked()){
                    database.addContent(contents.get(position),"FRIDAY");
                }
                if(saturdayCB.isChecked()){
                    database.addContent(contents.get(position),"SATURDAY");
                }
                if(sundayCB.isChecked()){
                    database.addContent(contents.get(position),"SUNDAY");
                }
                setRecyclerView(database.getContents("FRIDAY"));
                epicDialog.dismiss();
            }
        });
        closePopupBtn=(ImageButton) epicDialog.findViewById(R.id.CloseBtn);


        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void moveDialog(final Dialog epicDialog, final int position) {

        completePopupBtn=(Button) epicDialog.findViewById(R.id.CompleteBtn);
        completePopupBtn.setOnClickListener(new View.OnClickListener() {
            RadioGroup weekRG=epicDialog.findViewById(R.id.weekRG);
            String day="WEDNESDAY";
            @Override
            public void onClick(View v) {

                switch (weekRG.getCheckedRadioButtonId()){
                    case R.id.MondayRB:
                        day="MONDAY";
                        break;
                    case R.id.TuesdayRB:
                        day="TUESDAY";
                        break;
                    case R.id.WednesdayRB:
                        day="WEDNESDAY";
                        break;
                    case R.id.ThursdayRB:
                        day="THURSDAY";
                        break;
                    case R.id.FridayRB:
                        day="FRIDAY";
                        break;
                    case R.id.SaturdayRB:
                        day="SATURDAY";
                        break;
                    case R.id.SundayRB:
                        day="SUNDAY";
                        break;
                }
                database.addContent(contents.get(position),day);
                deleteContent(contents.get(position));
                setRecyclerView(database.getContents("WEDNESDAY"));
                epicDialog.dismiss();
            }
        });
        closePopupBtn=(ImageButton) epicDialog.findViewById(R.id.CloseBtn);


        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();

    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if(!isPermanent){
                deleteTemporaryContent(temporaryContents.get(viewHolder.getAdapterPosition()));
            }
            if(isPermanent){
                deleteContent(contents.get(viewHolder.getAdapterPosition()));
                database.deleteAllContent("TEMPORARY");
            }
        }
    };

    private void deleteTemporaryContent(Content temporaryContent) {
        temporaryContents.remove(temporaryContent);
        database.deleteContent(temporaryContent.getID(),"TEMPORARY");
        if(!contents.isEmpty()){
            for(int i=0;i<contents.size();i++){
                if(contents.get(i).getTitle().equals(temporaryContent.getTitle()) && contents.get(i).getTimeStart().equals(temporaryContent.getTimeStart()) && contents.get(i).getPriority()==(temporaryContent.getPriority()) && (!contents.get(i).isPermanent() && !temporaryContent.isPermanent())) {
                    deleteContent(contents.get(i));
                }
            }
        }
    }
}
