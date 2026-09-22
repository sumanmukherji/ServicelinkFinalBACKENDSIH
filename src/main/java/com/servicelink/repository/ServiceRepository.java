package com.servicelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicelink.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}