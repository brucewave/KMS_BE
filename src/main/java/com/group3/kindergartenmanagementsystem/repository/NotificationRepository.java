package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
