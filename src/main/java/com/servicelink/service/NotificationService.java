package com.servicelink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicelink.entity.Notification;
import com.servicelink.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getNotifications(String customerName) {
        return notificationRepository.findByCustomerName(customerName);
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}