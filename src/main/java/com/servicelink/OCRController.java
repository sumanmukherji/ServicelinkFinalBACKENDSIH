package com.servicelink;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin(origins = "*")
public class OCRController {

    private final OCRService ocrService;

    public OCRController(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Please select a file.");
            }

            String uploadFolder = "uploads";

            Path uploadPath = Paths.get(uploadFolder);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis()
                    + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            String extractedText =
                    ocrService.extractText(filePath.toString());

            Map<String, Object> response = new HashMap<>();

            response.put("fileName", file.getOriginalFilename());
            response.put("message", "OCR completed successfully");
            response.put("extractedText", extractedText);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body("OCR processing failed: " + e.getMessage());
        }
    }
}