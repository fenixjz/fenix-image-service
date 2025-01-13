package com.fenix.fenix_image_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
class FenixImageHelper {

    /**
     * Determines the format of an image file based on its extension.
     *
     * @param file the image file
     * @return the format name (e.g., "jpg", "png")
     * @throws RuntimeException if the format cannot be determined
     */
    String getFormatName(File file) {
        try {
            return ImageIO.getImageReadersBySuffix(getFileExtension(file)).next().getFormatName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the file extension from a given file name.
     *
     * @param file the file
     * @return the file extension in lowercase (e.g., "jpg", "png")
     * @throws IllegalArgumentException if the file does not have an extension
     */
    String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex == -1) {
            throw new IllegalArgumentException("File does not have an extension: " + name);
        }
        return name.substring(lastIndex + 1).toLowerCase();
    }

    /**
     * Retrieves a list of supported image formats for writing.
     *
     * @return a list of supported image format names (e.g., "jpg", "png", "bmp")
     */
    List<String> imgFormats() {
        List<String> formats = new ArrayList<>();
        Collections.addAll(formats, ImageIO.getWriterFormatNames());
        return formats;
    }

    /**
     * Validates whether the provided image format is supported.
     * If the format is not supported, an exception is thrown.
     *
     * @param formatName the name of the image format to be validated (e.g., "jpg", "png", "bmp")
     * @throws IllegalArgumentException if the provided image format is not supported
     */
    void formatException(String formatName) {
        if (!imgFormats().contains(formatName.toLowerCase())) {
            throw new IllegalArgumentException("Unsupported image format: " + formatName);
        }
    }
}
