package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Notification;
import com.group3.kindergartenmanagementsystem.payload.NotificationDTO;
import com.group3.kindergartenmanagementsystem.repository.NotificationRepository;
import com.group3.kindergartenmanagementsystem.service.NotificationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    ModelMapper mapper;
    @Override
    public NotificationDTO getNotificationById(Integer id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Notification", "id", id));
        return mapToDTO(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotification() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public NotificationDTO createNewNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setId(null);
        notification.setTitle(notificationDTO.getTitle());
        notification.setContent(notificationDTO.getContent());
        notification.setPostDate(LocalDateTime.now());
        Notification newNotification = notificationRepository.save(notification);
        return mapToDTO(newNotification);
    }

    @Override
    public NotificationDTO updateNotificationById(Integer id, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Notification", "id", id));
        notification.setContent(notificationDTO.getContent());
        notification.setTitle(notificationDTO.getTitle());
        notification.setPostDate(notificationDTO.getPostDate());
        Notification updatedNotification = notificationRepository.save(notification);
        return mapToDTO(updatedNotification);
    }

    @Override
    public void deleteNotificationById(Integer id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Notification", "id", id));
        notificationRepository.delete(notification);
    }

    private NotificationDTO mapToDTO(Notification notification){
        return mapper.map(notification, NotificationDTO.class);
    }

//    private Notification mapToEntity(NotificationDTO notificationDTO){
//        return mapper.map(notificationDTO, Notification.class);
//    }
}
