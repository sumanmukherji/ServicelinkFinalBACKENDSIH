package com.servicelink;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;

@Service
public class OCRService {

    public String extractText(String imagePath) {

        Tesseract tesseract = new Tesseract();

        String tessDataPath = System.getenv(
                "TESSDATA_PREFIX"
        );

        if (tessDataPath == null || tessDataPath.isBlank()) {

            tessDataPath =
                    "C:\\Program Files\\Tesseract-OCR\\tessdata";
        }

        tesseract.setDatapath(tessDataPath);
        tesseract.setLanguage("eng");

        ImageIO.setUseCache(false);

        try {

            BufferedImage image =
                    ImageIO.read(new File(imagePath));

            if (image == null) {
                return "Unable to read image";
            }

            return tesseract.doOCR(image);

        } catch (Exception e) {

            e.printStackTrace();

            return "OCR processing failed: "
                    + e.getMessage();
        }
    }
}