package com.kor.foodmanager.data.model;

import java.util.List;

public class NotificationListDto {
    private List<NotificationDto> notifications;

    public NotificationListDto() {
    }

    public NotificationListDto(List<NotificationDto> notifications) {
        this.notifications = this.notifications;
    }

    public List<NotificationDto> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDto> notifications) {
        this.notifications = notifications;
    }
}
