package com.kor.foodmanager.ui.eventList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kor.foodmanager.R;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>  {

    private ArrayList<MyEvent> events;
    private MyClickListener listener;

    public EventListAdapter(ArrayList<MyEvent> events){
        this.events=events;
    }

    public void setListener(MyClickListener listener) {
        this.listener = listener;
    }

    public int getEventCount() {
        return events.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_in_list,viewGroup, false);
        Log.d("MY_TAG", "onCreateViewHolder: ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Log.d("MY_TAG", "onBindViewHolder: ");
        MyEvent event = events.get(i);
        myViewHolder.familyName.setText(event.getFamilyName());
        myViewHolder.eventTitle.setText(event.getEventTitle());
        myViewHolder.eventDate.setText(event.getEventDate());


    }

    public void addEvent(MyEvent event){
        events.add(event);
        Log.d("MY_TAG", "addEvent: ");
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public ArrayList<MyEvent> getEvents() {
        return events;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView familyName, eventTitle, eventDate;
        private ToggleButton favBtn;
        private RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            familyName = itemView.findViewById(R.id.family_name);
            eventTitle = itemView.findViewById(R.id.event_title);
            eventDate = itemView.findViewById(R.id.event_date);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });

            favBtn = itemView.findViewById(R.id.fav_btn);
            favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // TODO The toggle is enabled
                    } else {
                        // TODO The toggle is disabled
                    }
                }
            });

        }
    }

    public interface MyClickListener{
        void onItemClick(int position);
    }

}



