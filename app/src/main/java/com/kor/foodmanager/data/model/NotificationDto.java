package com.kor.foodmanager.data.model;

import java.util.Date;

public class NotificationDto {
    private int notificationId, eventId;
    private String title, message, type;
    private Boolean isRead;
    private Date date;

    public NotificationDto() {
    }

    public NotificationDto(int notificationId, int eventId, String title, String message, String type, Boolean isRead, Date date) {
        this.notificationId = notificationId;
        this.eventId = eventId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.date = date;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //     "notificationId": 4,++
//             "title": "Invitation",++
//             "message": "You was invited to event 'Pesah'",++
//             "date": "2018-03-19",++
//             "type": "System",++
//             "isRead": false,++
//             "eventId": 125++
}
