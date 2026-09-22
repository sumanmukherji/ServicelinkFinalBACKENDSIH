package com.servicelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicelink.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}