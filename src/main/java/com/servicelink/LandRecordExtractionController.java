package com.servicelink;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/land-records/extract")
@CrossOrigin(origins = "*")
public class LandRecordExtractionController {

    private final LandRecordExtractionService extractionService;

    public LandRecordExtractionController(
            LandRecordExtractionService extractionService) {
        this.extractionService = extractionService;
    }

    @PostMapping
    public ResponseEntity<LandRecord> extract(
            @RequestBody Map<String, String> request) {

        String ocrText = request.get("ocrText");

        if (ocrText == null || ocrText.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        LandRecord record =
                extractionService.extractLandRecord(ocrText);

        return ResponseEntity.ok(record);
    }
}