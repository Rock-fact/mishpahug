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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ParticipationListAdapter extends RecyclerView.Adapter<ParticipationListAdapter.MyViewHolder>{

    private List<EventDto> participationList;
    private EventListAdapter.MyClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participated_event_in_list,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setListener(EventListAdapter.MyClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.event_title) TextView eventTitle;
        @BindView(R.id.event_date) TextView eventDate;
        @BindView(R.id.event_description) TextView eventDescription;
        @BindView(R.id.event_status_txt) TextView eventStatus;
        private Unbinder unbinder;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this, itemView);
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
