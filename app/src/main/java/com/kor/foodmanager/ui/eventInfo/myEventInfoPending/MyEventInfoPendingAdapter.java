package com.kor.foodmanager.ui.eventInfo.myEventInfoPending;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.CropCircleTransformation;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyEventInfoPendingAdapter extends RecyclerView.Adapter<MyEventInfoPendingAdapter.MyViewHolder>{

    private Long eventId;
    private List<UserDto> listOfParticipants;
    private MyClickListener listener;

    public MyEventInfoPendingAdapter() {
        listOfParticipants = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subscriber,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        UserDto subscriber = listOfParticipants.get(i);
        myViewHolder.fullName.setText(subscriber.getFullName());
        Picasso.get().invalidate(subscriber.getPictureLink().get(0));
        Picasso.get().load(subscriber.getPictureLink().get(0)).memoryPolicy(MemoryPolicy.NO_CACHE)
                .transform(new CropCircleTransformation())
                .networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.logo).into(myViewHolder.pictureOfSubscriber);
         //TODO load image
    }

    @Override
    public int getItemCount() {
        return listOfParticipants.size();
    }

    public void setListener(MyClickListener listener) {
        this.listener = listener;
    }

    public void setSubscribers(List<UserDto> listOfParticipants, Long eventId){
        this.listOfParticipants=listOfParticipants;
        this.eventId=eventId;
        notifyDataSetChanged();
    }
    public void setSubscribers(List<UserDto> listOfParticipants){
        this.listOfParticipants=listOfParticipants;
        notifyDataSetChanged();
    }


    public void removeAll(){
        listOfParticipants.clear();
        notifyDataSetChanged();
    }

    public List<UserDto> getListOfParticipants() {
        return listOfParticipants;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fullName;
        ImageView pictureOfSubscriber;
        Button inviteBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.subscriber_full_name);
            pictureOfSubscriber = itemView.findViewById(R.id.subscriber_picture);
            inviteBtn = itemView.findViewById(R.id.invite_btn);
            inviteBtn.setVisibility(View.GONE);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.userInfo(getAdapterPosition());
                }
            });

        }
    }
    public interface MyClickListener{
        void userInfo(int adapterPosition);
    }
}
