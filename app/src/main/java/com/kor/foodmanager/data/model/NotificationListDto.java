package com.kor.foodmanager.data.model;

import java.util.List;

public class NotificationListDto {
    private List<NotificationDto> notificationList;

    public NotificationListDto() {
    }

    public NotificationListDto(List<NotificationDto> notificationList) {
        this.notificationList = notificationList;
    }

    public List<NotificationDto> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationDto> notificationList) {
        this.notificationList = notificationList;
    }
}
