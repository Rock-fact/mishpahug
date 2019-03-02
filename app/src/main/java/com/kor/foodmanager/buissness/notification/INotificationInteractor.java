package com.kor.foodmanager.buissness.notification;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.NotificationDto;
import com.kor.foodmanager.data.model.NotificationListDto;

import java.io.IOException;
import java.util.List;

public interface INotificationInteractor {
    List<NotificationDto> getNotificationList() throws IOException, ServerException;
}
