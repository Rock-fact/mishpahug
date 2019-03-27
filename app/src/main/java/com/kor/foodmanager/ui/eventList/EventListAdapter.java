package com.kor.foodmanager.ui.eventList;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private ArrayList<EventDto> events;
    private MyClickListener listener;

    public EventListAdapter() {
        events = new ArrayList<>();
    }
    public EventListAdapter(ArrayList<EventDto> events) {
        this.events = events;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                return new MyViewHolder(
                        LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.event_in_list, viewGroup, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.progress_frame, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == events.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {

        return events==null?0:events.size();
    }

    public void addEvent(EventDto event) {
        events.add(event);
        notifyItemChanged(events.size()-1);
    }

    public void addAll(List<EventDto> events) {
        for (EventDto event : events) {
            addEvent(event);
        }
    }
    private void remove(EventDto event) {
        int position = events.indexOf(event);
        if (position > -1) {
            events.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        addEvent(new EventDto());
        Log.d("Vova", "addLoading: "+events.size());
    }

    public void removeLoading() {
        int position = events.size() - 1;
        if (position >= 0) {
        Log.d("VOVA", "removeLoading: " + (position + 1));
        isLoaderVisible = false;
        EventDto event = getItem(position);
        if (event != null) {
            events.remove(position);
            notifyItemRemoved(position);
        }
    }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    EventDto getItem(int position) {
        return events.get(position);
    }


    public void setListener(MyClickListener listener) {
        this.listener = listener;
    }


    public ArrayList<EventDto> getEvents() {
        return events;
    }


    class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.family_name)
        TextView familyName;
        @BindView(R.id.event_title)
        TextView eventTitle;
        @BindView(R.id.event_date)
        TextView eventDate;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.event_img)
        ImageView eventImg;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            EventDto event = events.get(position);
            Log.d("MY_TAG", "onBind: "+(event.getOwner()!=null));

                familyName.setText(event.getOwner().getFullName());
                eventTitle.setText(event.getTitle());
                eventDate.setText(event.getDate().toString());
                Log.d("MY_TAG", "EventId: " + event.getEventId());
                Log.d("MY_TAG", "Rate: " + event.getOwner().getRate());

                ratingBar.setRating(new Float(event.getOwner().getRate())); //TODO
                if (event.getOwner().getPictureLink().size() > 0 && event.getOwner().getPictureLink().get(0) != null) {
                    //Picasso.get().load(event.getOwner().getPictureLink().get(0)).into(myViewHolder.eventImg); //TODO
                    Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(eventImg);
                } else {
                    Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(eventImg);
                }

            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void clear() {

        }
    }

    public class FooterHolder extends BaseViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar progressBar;


        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void clear() {

        }

    }

    public interface MyClickListener {
        void onItemClick(int position);
    }

}



