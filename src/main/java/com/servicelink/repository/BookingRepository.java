package com.servicelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicelink.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
