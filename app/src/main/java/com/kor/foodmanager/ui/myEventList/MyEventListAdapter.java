package com.kor.foodmanager.ui.myEventList;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;

import java.util.ArrayList;
import java.util.List;

public class MyEventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String INPROGRESS = "In progress";
    public static final String PENDING = "Pending";
    public static final String DONE = "Done";

    private ArrayList<EventDtoWithStatus> events;
    private MyClickListener listener;

    public MyEventListAdapter() {
        events = new ArrayList<>();
    }

    public void setEvents(List<EventDto> eventsDto) {
        ArrayList<EventDtoWithStatus> events = new ArrayList<>();
        Log.d("MY_TAG", "onPostExecute: " + eventsDto.size());
        if (eventsDto.size() > 0) {

            String status;
            status = eventsDto.get(0).getStatus();
            events.add(new EventDtoWithStatus(status));
            for (int i = 0; i < eventsDto.size(); i++) {
                Log.d("MY_TAG", "status i = " + eventsDto.get(i).getStatus() + "status cat = " + status);
                if (!status.equals(eventsDto.get(i).getStatus())) {
                    status = eventsDto.get(i).getStatus();
                    events.add(new EventDtoWithStatus(status));
                    events.add(new EventDtoWithStatus(eventsDto.get(i)));
                } else {
                    events.add(new EventDtoWithStatus(eventsDto.get(i)));
                }
            }
        }
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
        if (i == 0) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.status_my_event_row, viewGroup, false);
            holder = new StatusViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.my_event_row, viewGroup, false);
            holder = new MyEventViewHolder(view);
        }
        return holder;
    }

    private String defineStatus(String status) {
        switch (status) {
            case "In progress":
                return MyEventListAdapter.INPROGRESS;
            case "Pending":
                return MyEventListAdapter.PENDING;
            case "Done":
                return MyEventListAdapter.DONE;
        }
        return "IsAbsent";
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        EventDto event = events.get(i).getEvent();
        if (holder instanceof StatusViewHolder) {
            ((StatusViewHolder) holder).status.setText(defineStatus(event.getStatus()));
        } else if (holder instanceof MyEventViewHolder) {
            ((MyEventViewHolder) holder).title.setText(event.getTitle());
            ((MyEventViewHolder) holder).date.setText(event.getDate());
            ((MyEventViewHolder) holder).description.setText(event.getDescription());
            ((MyEventViewHolder) holder).status.setText(defineStatus(event.getStatus()));
            switch (event.getStatus()) {
                case INPROGRESS:
                    ((MyEventViewHolder) holder).amount.setText("Subscribed: " + event.getParticipants().size() + " people");
                    break;
                case PENDING:
                    ((MyEventViewHolder) holder).amount.setText("Will go: " + event.getParticipants().size() + " people");
                    break;
                case DONE:
                    ((MyEventViewHolder) holder).amount.setText("");
                    break;
                default:
                    ((MyEventViewHolder) holder).amount.setText("None data");
            }
        }
    }

    class StatusViewHolder extends RecyclerView.ViewHolder {
        private TextView status;

        public StatusViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status_row);
        }
    }

    class MyEventViewHolder extends RecyclerView.ViewHolder {
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

    public void removeAll() {
        events.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public ArrayList<EventDtoWithStatus> getEvents() {
        return events;
    }


    public interface MyClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemViewType(int position) {
        return events.get(position).getType().equals(EventDtoWithStatus.STATUS) ? 0 : 1;
    }
}
