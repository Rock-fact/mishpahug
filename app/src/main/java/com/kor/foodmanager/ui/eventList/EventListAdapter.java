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
import com.kor.foodmanager.data.model.EventDto;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>  {

    private ArrayList<EventDto> events;
    private MyClickListener listener;

    public EventListAdapter() {
        events = new ArrayList<>();
    }

    public EventListAdapter(ArrayList<EventDto> events){
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
        EventDto event = events.get(i);
        myViewHolder.familyName.setText(event.getOwner().getFullName());
        myViewHolder.eventTitle.setText(event.getTitle());
        myViewHolder.eventDate.setText(event.getDate().toString());
        Log.d("MY_TAG", "EventId: "+event.getEventId());
        Log.d("MY_TAG", "Rate: "+event.getOwner().getRate());
        myViewHolder.ratingBar.setRating(new Float(event.getOwner().getRate())); //TODO
    }

    public void addEvent(EventDto event){
        events.add(event);
        Log.d("MY_TAG", "addEvent: ");
        notifyDataSetChanged();
    }

    public void removeAll(){
        events.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public ArrayList<EventDto> getEvents() {
        return events;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView familyName, eventTitle, eventDate;
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

           

        }
    }

    public interface MyClickListener{
        void onItemClick(int position);
    }

}



