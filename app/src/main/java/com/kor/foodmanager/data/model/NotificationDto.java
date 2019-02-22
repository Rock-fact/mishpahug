package com.kor.foodmanager.data.model;

import java.util.Date;

public class NotificationDto {
    private Long eventId, notificationId;
    private String title, message, type;
    private Boolean isRead;
    private Date date;

    public NotificationDto() {
    }

    public NotificationDto(Long notificationId, Long eventId, String title, String message, String type, Boolean isRead, Date date) {
        this.notificationId = notificationId;
        this.eventId = eventId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.date = date;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
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
