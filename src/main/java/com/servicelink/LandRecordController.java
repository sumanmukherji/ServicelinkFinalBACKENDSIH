package com.servicelink;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/land-records")
@CrossOrigin(origins = "*")
public class LandRecordController {

    private final LandRecordRepository repository;
    private final LandRecordExtractionService extractionService;

    public LandRecordController(
            LandRecordRepository repository,
            LandRecordExtractionService extractionService) {

        this.repository = repository;
        this.extractionService = extractionService;
    }

    // Add Land Record manually
    @PostMapping
    public LandRecord addRecord(@RequestBody LandRecord record) {

        validateRecord(record);

        return repository.save(record);
    }

    // Extract OCR text and save directly to database
    @PostMapping("/extract-and-save")
    public ResponseEntity<LandRecord> extractAndSave(
            @RequestBody Map<String, String> request) {

        String ocrText = request.get("ocrText");

        if (ocrText == null || ocrText.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Extract structured data from OCR text
        LandRecord record =
                extractionService.extractLandRecord(ocrText);

        // Apply duplicate and conflict validation
        validateRecord(record);

        // Save to MySQL / Aiven database
        LandRecord savedRecord =
                repository.save(record);

        return ResponseEntity.ok(savedRecord);
    }

    // Get all land records
    @GetMapping
    public List<LandRecord> getAllRecords() {
        return repository.findAll();
    }

    // Get single land record
    @GetMapping("/{id}")
    public LandRecord getRecord(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Land record not found"));
    }

    // Verify land record
    @PutMapping("/{id}/verify")
    public LandRecord verifyRecord(@PathVariable Long id) {

        LandRecord record = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Land record not found"));

        record.setStatus("Verified");
        record.setConflict(false);

        return repository.save(record);
    }

    // Reject land record
    @PutMapping("/{id}/reject")
    public LandRecord rejectRecord(@PathVariable Long id) {

        LandRecord record = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Land record not found"));

        record.setStatus("Rejected");

        return repository.save(record);
    }

    // Duplicate and conflict validation
    private void validateRecord(LandRecord record) {

        boolean duplicate = false;

        if (record.getSurveyNo() != null &&
                !record.getSurveyNo().isBlank() &&
                repository.existsBySurveyNo(record.getSurveyNo())) {

            duplicate = true;
        }

        if (record.getKhataNo() != null &&
                !record.getKhataNo().isBlank() &&
                repository.existsByKhataNo(record.getKhataNo())) {

            duplicate = true;
        }

        record.setDuplicate(duplicate);

        boolean conflict =
                record.getOwnerName() == null ||
                record.getOwnerName().isBlank() ||

                record.getSurveyNo() == null ||
                record.getSurveyNo().isBlank() ||

                record.getKhataNo() == null ||
                record.getKhataNo().isBlank() ||

                record.getArea() == null ||
                record.getArea().isBlank();

        record.setConflict(conflict);

        if (duplicate || conflict) {
            record.setStatus("Needs Verification");
        } else {
            record.setStatus("Pending");
        }
    }
}
