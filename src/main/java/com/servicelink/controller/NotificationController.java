package com.servicelink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicelink.entity.Notification;
import com.servicelink.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications(
            @RequestParam String customerName) {

        return notificationService
                .getNotifications(customerName);
    }

    @PostMapping
    public Notification addNotification(
            @RequestBody Notification notification) {

        return notificationService
                .saveNotification(notification);
    }
}