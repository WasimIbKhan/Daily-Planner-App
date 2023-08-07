package uk.co.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventVH>{
    Context context;
    ArrayList<Content> eventContents;
    private LayoutInflater inflater;
    private OnEventListener onEventListener;

    public EventAdapter(Context context, ArrayList<Content> eventContents, OnEventListener onEventListener){
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        this.eventContents=eventContents;
        this.onEventListener=onEventListener;
    }
    @NonNull
    @Override
    public EventAdapter.EventVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_row,parent,false);
        return new EventVH(view,onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventVH holder, int position) {
        holder.title.setText(eventContents.get(position).getTitle());
        String time=eventContents.get(position).getTimeStart()+context.getResources().getString(R.string.Hyphon)+eventContents.get(position).getTimeEnd();
        holder.time.setText(time);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(convertStringToDate(eventContents.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = ""+cal.get(Calendar.DAY_OF_MONTH);
        String monthName=theMonth(cal.get(Calendar.MONTH));
        String year=""+cal.get(Calendar.YEAR);

        holder.day.setText(day);
        holder.month.setText(monthName);
        holder.year.setText(year);

        switch (eventContents.get(position).getActivity()){
            case 0:
                holder.event.setText(R.string.Corporate);
                holder.title.setTextColor(Color.parseColor("#661183"));
                holder.time.setTextColor(Color.parseColor("#661183"));
                holder.day.setTextColor(Color.parseColor("#661183"));
                holder.month.setTextColor(Color.parseColor("#661183"));
                holder.year.setTextColor(Color.parseColor("#661183"));
                holder.event.setBackgroundResource(R.drawable.round_purple);
                break;
            case 1:
                holder.event.setText(R.string.Private);
                holder.title.setTextColor(Color.parseColor("#104275"));
                holder.time.setTextColor(Color.parseColor("#104275"));
                holder.day.setTextColor(Color.parseColor("#104275"));
                holder.month.setTextColor(Color.parseColor("#104275"));
                holder.year.setTextColor(Color.parseColor("#104275"));
                holder.event.setBackgroundResource(R.drawable.round_crimson);
                break;
            case 2:
                holder.event.setText(R.string.Party);
                holder.title.setTextColor(Color.parseColor("#960e29"));
                holder.time.setTextColor(Color.parseColor("#960e29"));
                holder.day.setTextColor(Color.parseColor("#960e29"));
                holder.month.setTextColor(Color.parseColor("#960e29"));
                holder.year.setTextColor(Color.parseColor("#960e29"));
                holder.event.setBackgroundResource(R.drawable.round_blue);
                break;
            case 3:
                holder.event.setText(R.string.Celebration);
                holder.title.setTextColor(Color.parseColor("#bd930b"));
                holder.time.setTextColor(Color.parseColor("#bd930b"));
                holder.day.setTextColor(Color.parseColor("#bd930b"));
                holder.month.setTextColor(Color.parseColor("#bd930b"));
                holder.year.setTextColor(Color.parseColor("#bd930b"));
                holder.leftView.setBackgroundColor(Color.parseColor("#bd930b"));
                holder.event.setBackgroundResource(R.drawable.round_gold);
                break;
            case 4:
                holder.event.setText(R.string.Other);
                holder.title.setTextColor(Color.parseColor("#009a9a"));
                holder.time.setTextColor(Color.parseColor("#009a9a"));
                holder.day.setTextColor(Color.parseColor("#009a9a"));
                holder.month.setTextColor(Color.parseColor("#009a9a"));
                holder.year.setTextColor(Color.parseColor("#009a9a"));
                holder.event.setBackgroundResource(R.drawable.round_green);
                break;
        }
    }

    @Override
    public int getItemCount() { return eventContents.size(); }

    public class EventVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,day,month,year,time,event;
        ImageButton EditButton,DeleteButton,ShareButton;
        View leftView;
        OnEventListener onEventListener;

         public EventVH(@NonNull View itemView, final OnEventListener listener) {
            super(itemView);

             title=(TextView) itemView.findViewById(R.id.EventTitle);
             time=(TextView) itemView.findViewById(R.id.EventTime);
             day=(TextView) itemView.findViewById(R.id.eventDay);
             month=(TextView) itemView.findViewById(R.id.eventMonth);
             year=(TextView) itemView.findViewById(R.id.eventYear);
             event=(TextView) itemView.findViewById(R.id.EventActivity);


             itemView.setOnClickListener(this);
             this.onEventListener=listener;

             EditButton =(ImageButton) itemView.findViewById(R.id.EditEventButton);
             EditButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener !=null){
                         int position =getAdapterPosition();
                         if(position!=RecyclerView.NO_POSITION){
                             listener.onEditClick(position);
                         }
                     }
                 }
             });
             DeleteButton =(ImageButton) itemView.findViewById(R.id.DeleteEventButton);
             DeleteButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener !=null){
                         int position =getAdapterPosition();
                         if(position!=RecyclerView.NO_POSITION){
                             listener.onDeleteClick(position);
                         }
                     }
                 }
             });

             ShareButton =(ImageButton) itemView.findViewById(R.id.ShareEventButton);
             ShareButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener !=null){
                         int position =getAdapterPosition();
                         if(position!=RecyclerView.NO_POSITION){
                             listener.onDeleteClick(position);
                         }
                     }
                 }
             });
        }

        @Override
        public void onClick(View v) {
            onEventListener.onEventClick(getAdapterPosition());
        }
    }
    public interface OnEventListener{
        void onEventClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
        void onShareClick(int position);
    }

    public Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yy").parse(date);
    }
    private String theMonth(int month){
        String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        return monthNames[month];
    }
}
