package com.kor.foodmanager.ui.myEventList;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kor.foodmanager.R;

import java.util.ArrayList;

public class MyEventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static final String INPROGRESS = "INPROGRESS";
    public static final String PENDING = "PENDING";
    public static final String DONE = "DONE";

    private ArrayList<TitleRow> events;
    private MyClickListener listener;

    public MyEventListAdapter() {
        events = new ArrayList<>();
    }

    public void setEvents(ArrayList<TitleRow> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public void setListener(MyClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        if(i == 0) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.status_my_event_row,viewGroup,false);
            holder = new StatusViewHolder(view);
        }else{
            View view  = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.my_event_row,viewGroup, false);
            holder = new MyEventViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        TitleRow event = events.get(i);
        if(holder instanceof StatusViewHolder){
            ((StatusViewHolder) holder).status.setText(event.getStatus());
        }
        else if(holder instanceof MyEventViewHolder){
            ((MyEventViewHolder)holder).title.setText(event.getTitle());
            ((MyEventViewHolder)holder).date.setText(event.getDate());
            ((MyEventViewHolder)holder).description.setText(event.getDescription());
            ((MyEventViewHolder)holder).status.setText(event.getStatus());
            switch (event.getStatus()){
                case INPROGRESS:
                    ((MyEventViewHolder)holder).amount.setText("Subscribed: " +event.getAmountOfParticipants()+" people");
                case PENDING:
                    ((MyEventViewHolder)holder).amount.setText("Will go: " +event.getAmountOfParticipants()+" people");
                case DONE:
                    ((MyEventViewHolder)holder).amount.setText("");
            }
        }
    }

    class StatusViewHolder extends RecyclerView.ViewHolder{
        private TextView status;
        public StatusViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status_row);
        }
    }

    class MyEventViewHolder extends RecyclerView.ViewHolder{
        private TextView title, date, description, status, amount;
        public MyEventViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.event_title);
            date = itemView.findViewById(R.id.event_date);
            description = itemView.findViewById(R.id.event_description);
            status = itemView.findViewById(R.id.status_txt);
            amount = itemView.findViewById(R.id.amountOfParticipants);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public void removeAll(){
        events.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public ArrayList<TitleRow> getEvents() {
        return events;
    }


    public interface MyClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemViewType(int position) {
        return events.get(position).getType().equals(TitleRow.STATUS)?0:1;
    }
}
