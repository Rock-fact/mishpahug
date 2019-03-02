package com.kor.foodmanager.ui.notificationList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.NotificationDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {
    List<NotificationDto> list = new ArrayList<>();

    public NotificationListAdapter() {
    }

    public void setList(List<NotificationDto> list){
        this.list = list;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item,viewGroup,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int i) {
        NotificationDto notification = list.get(i);
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        holder.date.setText(formatDate.format(notification.getDate()));         // TODO: 02.03.2019  Date in dto?
        Log.d(TAG, notification.toString());
        if(notification.getRead()==null||notification.getRead()==false){
            holder.newNotification.setVisibility(View.VISIBLE);
        }else {
            holder.newNotification.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView title, message, date, newNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            newNotification = itemView.findViewById(R.id.new_notification);
        }
    }
}
