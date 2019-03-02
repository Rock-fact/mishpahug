package com.kor.foodmanager.buissness.notification;

import com.kor.foodmanager.data.model.NotificationDto;

import java.util.Comparator;

class NotificationComparator implements Comparator<NotificationDto> {
    @Override
    public int compare(NotificationDto o1, NotificationDto o2) {
        Boolean b1 = o1.getRead();
        Boolean b2 = o2.getRead();
        if(b1 == null){
            b1 = false;
        }
        if(b2 == null){
            b2 = false;
        }
        int res;
        res =  Boolean.compare(b2, b1);
        if(res==0){
            res = o2.getDate().compareTo(o1.getDate());
        }
        return res;
    }
}
