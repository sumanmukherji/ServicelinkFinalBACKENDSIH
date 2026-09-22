package com.servicelink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicelink.entity.Booking;
import com.servicelink.entity.Notification;
import com.servicelink.service.BookingService;
import com.servicelink.service.NotificationService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;


    // ================= GET ALL BOOKINGS =================

    @GetMapping
    public List<Booking> getAllBookings() {

        return bookingService.getAllBookings();

    }


    // ================= ADD BOOKING =================

    @PostMapping
    public Booking addBooking(
            @RequestBody Booking booking) {

        return bookingService.saveBooking(booking);

    }


    // ================= WORKER BOOKINGS =================

    @GetMapping("/worker")
    public List<Booking> getWorkerBookings() {

        return bookingService.getAllBookings();

    }


    // ================= UPDATE BOOKING STATUS =================

    @PutMapping("/{id}/status")
    public Booking updateBookingStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Booking booking =
                bookingService.getBookingById(id);

        if (booking == null) {

            return null;

        }


        booking.setStatus(status);


        Booking updatedBooking =
                bookingService.saveBooking(booking);


        // ================= CREATE NOTIFICATION =================

        Notification notification =
                new Notification();

        notification.setCustomerName(
                booking.getCustomerName()
        );


        if (status.equalsIgnoreCase("Accepted")) {

            notification.setMessage(
                    "Your booking with "
                    + booking.getWorkerName()
                    + " has been accepted."
            );

        }
        else if (status.equalsIgnoreCase("Rejected")) {

            notification.setMessage(
                    "Your booking with "
                    + booking.getWorkerName()
                    + " has been rejected."
            );

        }
        else {

            notification.setMessage(
                    "Your booking status has been updated to "
                    + status + "."
            );

        }


        notification.setStatus("Unread");


        notificationService.saveNotification(
                notification
        );


        return updatedBooking;

    }


    // ================= CANCEL BOOKING =================

    @PutMapping("/{id}/cancel")
    public Booking cancelBooking(
            @PathVariable Long id) {

        Booking booking =
                bookingService.getBookingById(id);

        if (booking == null) {

            return null;

        }


        booking.setStatus("Cancelled");


        Booking cancelledBooking =
                bookingService.saveBooking(booking);


        // ================= CANCEL NOTIFICATION =================

        Notification notification =
                new Notification();

        notification.setCustomerName(
                booking.getCustomerName()
        );


        notification.setMessage(
                "Your booking with "
                + booking.getWorkerName()
                + " has been cancelled."
        );


        notification.setStatus("Unread");


        notificationService.saveNotification(
                notification
        );


        return cancelledBooking;

    }

}