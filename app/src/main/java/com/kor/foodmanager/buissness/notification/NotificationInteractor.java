package com.kor.foodmanager.buissness.notification;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.NotificationDto;
import com.kor.foodmanager.data.model.NotificationListDto;
import com.kor.foodmanager.data.notification.INotificationRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationInteractor implements INotificationInteractor {
    private INotificationRepository notificationRepository;
    private IAuthRepository authRepository;

    public NotificationInteractor(INotificationRepository notificationRepository, IAuthRepository authRepository) {
        this.notificationRepository = notificationRepository;
        this.authRepository = authRepository;
    }

    @Override
    public List<NotificationDto> getNotificationList() throws IOException, ServerException {
        String token = authRepository.getToken();
        NotificationListDto notificationListDto = notificationRepository.getNotificationList(token);
        ArrayList<NotificationDto> sortedList = new ArrayList<>(notificationListDto.getNotifications());
        Collections.sort(sortedList, new NotificationComparator());
        return sortedList;
    }
}
