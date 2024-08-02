package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.NotificationDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO getNotificationById(Integer id);
    List<NotificationDTO> getAllNotification();
    NotificationDTO createNewNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotificationById(Integer id, NotificationDTO notificationDTO);
    void deleteNotificationById(Integer id);
}
