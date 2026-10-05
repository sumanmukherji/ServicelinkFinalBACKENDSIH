package com.servicelink;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LandRecordRepository extends JpaRepository<LandRecord, Long> {

    boolean existsBySurveyNo(String surveyNo);

    boolean existsByKhataNo(String khataNo);

    long countByStatus(String status);

    long countByDuplicateTrue();

    long countByConflictTrue();
}