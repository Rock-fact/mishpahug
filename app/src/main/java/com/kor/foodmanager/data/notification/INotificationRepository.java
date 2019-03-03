package com.kor.foodmanager.data.notification;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.NotificationListDto;

import java.io.IOException;

public interface INotificationRepository {
    NotificationListDto getNotificationList(String token) throws IOException, ServerException;
    void notificationIsRead(String token, Long notificationId) throws IOException, ServerException;
}
