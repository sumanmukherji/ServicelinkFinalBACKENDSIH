package com.servicelink;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/land-records/dashboard")
@CrossOrigin(origins = "*")
public class LandRecordDashboardController {

    private final LandRecordRepository repository;

    public LandRecordDashboardController(
            LandRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Map<String, Long> dashboard() {

        Map<String, Long> data = new HashMap<>();

        data.put("totalRecords", repository.count());
        data.put("verified",
                repository.countByStatus("Verified"));
        data.put("pending",
                repository.countByStatus("Pending"));
        data.put("needsVerification",
                repository.countByStatus("Needs Verification"));
        data.put("rejected",
                repository.countByStatus("Rejected"));
        data.put("duplicates",
                repository.countByDuplicateTrue());
        data.put("conflicts",
                repository.countByConflictTrue());

        return data;
    }
}