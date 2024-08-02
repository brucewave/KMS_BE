package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.NotificationDTO;
import com.group3.kindergartenmanagementsystem.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@AllArgsConstructor
public class NotificationController {
    NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotification(){
        return ResponseEntity.ok(notificationService.getAllNotification());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Integer id){
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNewNotification(
            @RequestBody NotificationDTO notificationDTO){
        return new ResponseEntity<>(notificationService.createNewNotification(notificationDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Integer id){
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok("Delete notification successfully");
    }
}
