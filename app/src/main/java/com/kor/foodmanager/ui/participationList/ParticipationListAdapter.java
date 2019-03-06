package com.kor.foodmanager.ui.participationList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.eventList.EventListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ParticipationListAdapter extends RecyclerView.Adapter<ParticipationListAdapter.MyViewHolder>{

    private List<EventDto> participationList;
    private EventListAdapter.MyClickListener listener;

    public ParticipationListAdapter() {
        participationList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participated_event_in_list,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        EventDto event = participationList.get(i);
        myViewHolder.eventTitle.setText(event.getTitle());
        myViewHolder.eventDate.setText(event.getDate().toString());
        myViewHolder.eventDescription.setText(event.getDescription());
        myViewHolder.eventStatus.setText(event.getStatus().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return participationList.size();
    }

    public void setListener(EventListAdapter.MyClickListener listener) {
        this.listener = listener;
    }

    public void addEvent(EventDto event){
        participationList.add(event);
        notifyDataSetChanged();
    }

    public void removeAll(){
        participationList.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle, eventDate, eventDescription, eventStatus;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title);
            eventDate = itemView.findViewById(R.id.event_date);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventStatus = itemView.findViewById(R.id.event_status_txt);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }


    }

    public interface MyClickListener{
        void onItemClick(int position);
    }
}
