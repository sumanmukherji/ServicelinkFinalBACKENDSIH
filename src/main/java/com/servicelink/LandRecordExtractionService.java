package com.servicelink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class LandRecordExtractionService {

    public LandRecord extractLandRecord(String ocrText) {

        LandRecord record = new LandRecord();

        if (ocrText == null || ocrText.isBlank()) {
            return record;
        }

        // Clean OCR text
        String text = ocrText
                .replace("\r", "\n")
                .replaceAll("[ \t]+", " ")
                .trim();

        // Owner Name
        record.setOwnerName(
                extract(text,
                        "(?i)(?:owner\\s*name|owner|name)\\s*[:\\-]?\\s*([^\\n]+)")
        );

        // Survey Number
        record.setSurveyNo(
                extract(text,
                        "(?i)(?:survey\\s*(?:no|number)|plot\\s*(?:no|number))\\s*[:\\-]?\\s*([A-Za-z0-9\\-/]+)")
        );

        // Khata Number
        record.setKhataNo(
                extract(text,
                        "(?i)(?:khata\\s*(?:no|number)|khatian\\s*(?:no|number))\\s*[:\\-]?\\s*([A-Za-z0-9\\-/]+)")
        );

        // Area
        record.setArea(
                extract(text,
                        "(?i)(?:area|land\\s*area)\\s*[:\\-]?\\s*([0-9.,]+\\s*(?:acre|acres|hectare|hectares|ha|sq\\.?\\s*ft|sq\\.?\\s*m)?)")
        );

        // Village
        record.setVillage(
                extract(text,
                        "(?i)(?:village|gram)\\s*[:\\-]?\\s*([^\\n]+)")
        );

        // District
        record.setDistrict(
                extract(text,
                        "(?i)district\\s*[:\\-]?\\s*([^\\n]+)")
        );

        return record;
    }

    private String extract(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String value = matcher.group(1).trim();

            if (!value.isBlank()) {
                return value;
            }
        }

        return null;
    }
}