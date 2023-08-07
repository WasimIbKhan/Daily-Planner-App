package uk.co.myapplication.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.myapplication.Model.Content;
import uk.co.myapplication.R;
import uk.co.myapplication.ui.main.DoubleClickListener;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentVH> {
    @NonNull
    ArrayList<Content> contents;
    private LayoutInflater inflater;
    private Context context;
    private OnContentListener onContentListener;
    public static final int row_size_0 = 0;

    public ContentAdapter(Context context, ArrayList<Content> contentsList, OnContentListener onContentListener) {
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        this.contents=contentsList;
        this.onContentListener = onContentListener;
    }

    @Override
    public int getItemViewType(int position) {
        boolean event =(contents.get(position).isEvent());
        if(event){
            return 1;
        }
        Log.d("Size","getItemViewType: StringSize -> "+contents.get(position).getDescription().length());
        return 0;
    }
    public ContentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==row_size_0){
            View view=inflater.inflate(R.layout.row, parent,false);
            return new ContentVH(view, onContentListener);
        }else {
            View view=inflater.inflate(R.layout.row_event, parent,false);
            return new ContentVH(view, onContentListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ContentVH holder, int position) {

        holder.title.setText(contents.get(position).getTitle());
        String time=contents.get(position).getTimeStart()+context.getResources().getString(R.string.Hyphon)+contents.get(position).getTimeEnd();
        holder.time.setText(time);
        holder.description.setText(contents.get(position).getDescription());

        holder.address.setText(contents.get(position).getAddress());

        boolean isExpandable =contents.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE: View.GONE);

        boolean isExpandable2 =contents.get(position).isExpandable2();
        holder.expandableLayout2.setVisibility(isExpandable2? View.VISIBLE: View.GONE);

        switch (contents.get(position).getActivity()){
            case 0:
                holder.title.setTextColor(Color.parseColor("#661183"));
                holder.time.setTextColor(Color.parseColor("#661183"));
                holder.description.setTextColor(Color.parseColor("#661183"));
                holder.address.setTextColor(Color.parseColor("#661183"));
                holder.leftView.setBackgroundColor(Color.parseColor("#661183"));
                holder.leftBottomView.setBackgroundColor(Color.parseColor("#661183"));
                holder.leftBottomView2.setBackgroundColor(Color.parseColor("#661183"));
                break;
            case 1:
                holder.title.setTextColor(Color.parseColor("#104275"));
                holder.time.setTextColor(Color.parseColor("#104275"));
                holder.description.setTextColor(Color.parseColor("#104275"));
                holder.address.setTextColor(Color.parseColor("#104275"));
                holder.leftView.setBackgroundColor(Color.parseColor("#104275"));
                holder.leftBottomView.setBackgroundColor(Color.parseColor("#104275"));
                holder.leftBottomView2.setBackgroundColor(Color.parseColor("#104275"));
                break;
            case 2:
                holder.title.setTextColor(Color.parseColor("#960e29"));
                holder.time.setTextColor(Color.parseColor("#960e29"));
                holder.description.setTextColor(Color.parseColor("#960e29"));
                holder.address.setTextColor(Color.parseColor("#960e29"));
                holder.leftView.setBackgroundColor(Color.parseColor("#960e29"));
                holder.leftBottomView.setBackgroundColor(Color.parseColor("#960e29"));
                holder.leftBottomView2.setBackgroundColor(Color.parseColor("#960e29"));
                break;
            case 3:
                holder.title.setTextColor(Color.parseColor("#bd930b"));
                holder.time.setTextColor(Color.parseColor("#bd930b"));
                holder.description.setTextColor(Color.parseColor("#bd930b"));
                holder.address.setTextColor(Color.parseColor("#bd930b"));
                holder.leftView.setBackgroundColor(Color.parseColor("#bd930b"));
                holder.leftBottomView.setBackgroundColor(Color.parseColor("#bd930b"));
                holder.leftBottomView2.setBackgroundColor(Color.parseColor("#bd930b"));
                break;
            case 4:
                holder.title.setTextColor(Color.parseColor("#009a9a"));
                holder.time.setTextColor(Color.parseColor("#009a9a"));
                holder.description.setTextColor(Color.parseColor("#009a9a"));
                holder.address.setTextColor(Color.parseColor("#009a9a"));
                holder.leftView.setBackgroundColor(Color.parseColor("#009a9a"));
                holder.leftBottomView.setBackgroundColor(Color.parseColor("#009a9a"));
                holder.leftBottomView2.setBackgroundColor(Color.parseColor("#009a9a"));
                break;
        }
        if(contents.get(position).isEvent()){
            String activity;
            switch (contents.get(position).getActivity()) {
                case 0:
                    activity=context.getResources().getString(R.string.Corporate);
                    holder.state.setText(activity);
                    holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.state.setBackgroundResource(R.drawable.round_purple);
                    break;
                case 1:
                    activity=context.getResources().getString(R.string.Private);
                    holder.state.setText(activity);
                    holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.state.setBackgroundResource(R.drawable.round_crimson);
                    break;
                case 2:
                    activity=context.getResources().getString(R.string.Party);
                    holder.state.setText(activity);
                    holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.state.setBackgroundResource(R.drawable.round_blue);
                    break;
                case 3:
                    activity=context.getResources().getString(R.string.Celebration);
                    holder.state.setText(activity);
                    holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.state.setBackgroundResource(R.drawable.round_gold);
                    break;
                case 4:
                    activity=context.getResources().getString(R.string.Other);
                    holder.state.setText(activity);
                    holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.state.setBackgroundResource(R.drawable.round_green);
                    break;
            }
        }
        else {
            if(holder.priority!=null){
                String activity;
                switch (contents.get(position).getActivity()) {
                    case 0:
                        activity=context.getResources().getString(R.string.work_office_business);
                        holder.state.setText(activity);
                        holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.state.setBackgroundResource(R.drawable.round_purple);
                        break;
                    case 1:
                        activity=context.getResources().getString(R.string.social);
                        holder.state.setText(activity);
                        holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.state.setBackgroundResource(R.drawable.round_blue);
                        break;
                    case 2:
                        activity=context.getResources().getString(R.string.study_training_academia);
                        holder.state.setText(activity);
                        holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.state.setBackgroundResource(R.drawable.round_crimson);
                        break;
                    case 3:
                        activity=context.getResources().getString(R.string.Leisure);
                        holder.state.setText(activity);
                        holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.state.setBackgroundResource(R.drawable.round_gold);
                        break;
                    case 4:
                        activity=context.getResources().getString(R.string.breaks);
                        holder.state.setText(activity);
                        holder.state.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.state.setBackgroundResource(R.drawable.round_green);
                        break;
                }
            }
            switch (contents.get(position).getPriority()){
                case 0:
                    holder.priority.setText(R.string.Low);
                    holder.priority.setTextColor(Color.parseColor("#000000"));
                    holder.priority.setBackgroundResource(R.drawable.round_white);
                    break;
                case 1:
                    holder.priority.setText(R.string.Medium);
                    holder.priority.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.priority.setBackgroundResource(R.drawable.round_orange);
                    break;
                case 2:
                    holder.priority.setText(R.string.High);
                    holder.priority.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.priority.setBackgroundResource(R.drawable.round_red);
                    break;
                case 3:
                    holder.priority.setText(R.string.Top);
                    holder.priority.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.priority.setBackgroundResource(R.drawable.round_black);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + contents.get(position).getActivity());
            }
        }

    }


    @Override
    public int getItemCount() { return contents.size(); }

    public class ContentVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title,description,time,priority,state,address;
        ImageButton EditButton,ShareButton,MoreButton;
        LinearLayout linearLayout;
        ConstraintLayout expandableLayout,expandableLayout2;
        View leftView,leftBottomView,leftBottomView2;
        OnContentListener onContentListener;

        public ContentVH(@NonNull View itemView, final OnContentListener listener) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.TitleTextView);
            time=(TextView) itemView.findViewById(R.id.TimeTextView);
            description=(TextView) itemView.findViewById(R.id.DescriptionTextView);
            priority=(TextView) itemView.findViewById(R.id.priorityTextView);
            state=(TextView) itemView.findViewById(R.id.state);
            address=(TextView) itemView.findViewById(R.id.AddressTextView);

            itemView.setOnClickListener(this);
            this.onContentListener=listener;

            linearLayout=itemView.findViewById(R.id.linear_layout);
            expandableLayout=itemView.findViewById(R.id.expandable_description_layout);
            expandableLayout2=itemView.findViewById(R.id.expandable_address_layout);

            linearLayout.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    Content content=contents.get(getAdapterPosition());
                    content.setExpandable(!content.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }

                @Override
                public void onDoubleClick(View v) {
                    Content content=contents.get(getAdapterPosition());
                    content.setExpandable2(!content.isExpandable2());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            EditButton = (ImageButton) itemView.findViewById(R.id.EditButton);
            ShareButton =(ImageButton) itemView.findViewById(R.id.ShareButton);
            MoreButton=(ImageButton) itemView.findViewById(R.id.MoreButton);

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

            MoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        final int position =getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            PopupMenu popupMenu = new PopupMenu(context, v);
                            popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {

                                    switch(menuItem.getItemId()){
                                        case R.id.MoveToOption:
                                            listener.onMoreClick(position,"Move");
                                            break;
                                        case R.id.DuplicateToOption:
                                            listener.onMoreClick(position,"Duplicate");
                                            break;
                                        case R.id.SkipOption:
                                            listener.onMoreClick(position,"Skip");
                                            break;
                                    }
                                    return false;
                                }
                            });
                            popupMenu.show();
                        }
                    }
                }
            });
            leftView=(View) itemView.findViewById(R.id.view_leftNxt);
            leftBottomView=(View) itemView.findViewById(R.id.view_left_bottomNext);
            leftBottomView2=(View) itemView.findViewById(R.id.view_left_bottomNext2);

        }

        @Override
        public void onClick(View v) {
            onContentListener.onContentClick(getAdapterPosition());

        }
    }
    public interface OnContentListener{
        void onContentClick(int position);
        void onEditClick(int position);
        void onMoreClick(int position,String choice);
    }
}
