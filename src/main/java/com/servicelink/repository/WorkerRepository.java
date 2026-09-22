package com.servicelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicelink.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}