package com.fenix.fenix_image_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FenixImageService {

    private final FenixImageHelper imgHelp;

    /**
     * Resizes an image to the specified width and height while maintaining the aspect ratio.
     * If both width and height are specified, the method adjusts one dimension to preserve proportions.
     *
     * @param inputFile    the input image file to be resized
     * @param outputFile   the output file where the resized image will be saved
     * @param targetWidth  the target width of the image (set to 0 to calculate based on height)
     * @param targetHeight the target height of the image (set to 0 to calculate based on width)
     * @return the absolute path of the output file
     * @throws IllegalArgumentException if the image format is unsupported or dimensions are invalid
     * @throws RuntimeException         if an error occurs during processing
     */
    public String resize(File inputFile, File outputFile, int targetWidth, int targetHeight) {
        try {
            String formatName = imgHelp.getFormatName(inputFile);
            imgHelp.formatException(formatName);

            BufferedImage originalImage = ImageIO.read(inputFile);
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double aspectRatio = (double) originalWidth / originalHeight;

            if (targetWidth > 0 && targetHeight > 0) {
                double widthRatio = (double) targetWidth / originalWidth;
                double heightRatio = (double) targetHeight / originalHeight;

                if (widthRatio < heightRatio) {
                    targetHeight = (int) (targetWidth / aspectRatio);
                } else {
                    targetWidth = (int) (targetHeight * aspectRatio);
                }
            } else if (targetWidth > 0) {
                targetHeight = (int) (targetWidth / aspectRatio);
            } else if (targetHeight > 0) {
                targetWidth = (int) (targetHeight * aspectRatio);
            } else {
                throw new IllegalArgumentException("Target width or height must be greater than 0.");
            }

            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            ImageIO.write(resizedImage, formatName, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crops a portion of an image specified by the (x, y) coordinates and dimensions (width, height).
     * The cropped image is saved in the same format as the input file.
     *
     * @param inputFile  the input image file to be cropped
     * @param outputFile the output file where the cropped image will be saved
     * @param x          the x-coordinate of the top-left corner of the cropping rectangle
     * @param y          the y-coordinate of the top-left corner of the cropping rectangle
     * @param width      the width of the cropping rectangle
     * @param height     the height of the cropping rectangle
     * @return the absolute path of the output file
     * @throws IllegalArgumentException if the cropping rectangle exceeds the bounds of the image
     * @throws RuntimeException         if an error occurs during processing
     */
    public String crop(File inputFile, File outputFile, int x, int y, int width, int height) {
        try {
            String formatName = imgHelp.getFormatName(inputFile);
            imgHelp.formatException(formatName);

            BufferedImage originalImage = ImageIO.read(inputFile);
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            if (x < 0 || y < 0 || x + width > originalWidth || y + height > originalHeight) {
                throw new IllegalArgumentException("Cropping rectangle is outside the bounds of the image.");
            }

            BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);

            ImageIO.write(croppedImage, formatName, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error cropping image: " + e.getMessage(), e);
        }
    }

    /**
     * Rotates an image by the specified angle around its center.
     * The rotated image is saved in the same format as the input file.
     *
     * @param inputFile  the input image file to be rotated
     * @param outputFile the output file where the rotated image will be saved
     * @param angle      the angle in degrees for rotation (positive for clockwise, negative for counterclockwise)
     * @return the absolute path of the output file
     * @throws RuntimeException if an error occurs during processing
     */
    public String rotate(File inputFile, File outputFile, double angle) {
        try {
            String formatName = imgHelp.getFormatName(inputFile);
            imgHelp.formatException(formatName);

            BufferedImage originalImage = ImageIO.read(inputFile);
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());
            Graphics2D g2d = rotatedImage.createGraphics();

            g2d.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            ImageIO.write(rotatedImage, formatName, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error rotating image: " + e.getMessage(), e);
        }
    }

    /**
     * Converts an image from its current format to the specified target format.
     *
     * @param inputFile    the input image file to be converted
     * @param outputFile   the output file where the converted image will be saved
     * @param targetFormat the desired format of the output image (e.g., "jpg", "png", "bmp")
     * @return the absolute path of the output file
     * @throws IllegalArgumentException if the target format is unsupported
     * @throws RuntimeException         if an error occurs during processing
     */
    public String convert(File inputFile, File outputFile, String targetFormat) {
        try {
            BufferedImage originalImage = ImageIO.read(inputFile);
            imgHelp.formatException(targetFormat);

            ImageIO.write(originalImage, targetFormat, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error converting image format: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a text watermark to an image with specified opacity and position.
     *
     * @param inputFile    the input image file to be watermarked
     * @param outputFile   the output file where the watermarked image will be saved
     * @param watermarkText the text to be added as a watermark
     * @param x            the x-coordinate where the watermark will be placed
     * @param y            the y-coordinate where the watermark will be placed
     * @param opacity      the opacity of the watermark (range: 0.0 to 1.0)
     * @return the absolute path of the output file
     * @throws RuntimeException if an error occurs during processing
     */
    public String textWatermark(File inputFile, File outputFile, String watermarkText, int x, int y, float opacity) {
        try {
            String formatName = imgHelp.getFormatName(inputFile);
            imgHelp.formatException(formatName);
            BufferedImage originalImage = ImageIO.read(inputFile);

            Graphics2D g2d = (Graphics2D) originalImage.getGraphics();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.setColor(Color.WHITE);
            g2d.drawString(watermarkText, x, y);
            g2d.dispose();

            ImageIO.write(originalImage, formatName, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error adding watermark: " + e.getMessage(), e);
        }
    }

    /**
     * Inverts the colors of an image, creating a negative effect.
     *
     * @param inputFile  the input image file to be processed
     * @param outputFile the output file where the inverted image will be saved
     * @return the absolute path of the output file
     * @throws RuntimeException if an error occurs during processing
     */
    public String invert(File inputFile, File outputFile) {
        try {
            String formatName = imgHelp.getFormatName(inputFile);
            imgHelp.formatException(formatName);
            BufferedImage originalImage = ImageIO.read(inputFile);

            for (int y = 0; y < originalImage.getHeight(); y++) {
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    int rgba = originalImage.getRGB(x, y);
                    Color color = new Color(rgba, true);
                    Color invertedColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
                    originalImage.setRGB(x, y, invertedColor.getRGB());
                }
            }

            ImageIO.write(originalImage, formatName, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error inverting colors: " + e.getMessage(), e);
        }
    }

    /**
     * Converts an image to grayscale by averaging the RGB values of each pixel.
     *
     * @param inputFile  the input image file to be processed
     * @param outputFile the output file where the grayscale image will be saved
     * @return the absolute path of the output file
     * @throws RuntimeException if an error occurs during processing
     */
    public String grayscale(File inputFile, File outputFile) {
        try {
            String formatName = imgHelp.getFormatName(inputFile);
            imgHelp.formatException(formatName);
            BufferedImage originalImage = ImageIO.read(inputFile);

            for (int y = 0; y < originalImage.getHeight(); y++) {
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    int rgba = originalImage.getRGB(x, y);
                    Color color = new Color(rgba, true);
                    int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    Color grayColor = new Color(gray, gray, gray, color.getAlpha());
                    originalImage.setRGB(x, y, grayColor.getRGB());
                }
            }

            ImageIO.write(originalImage, formatName, outputFile);
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error applying grayscale: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a list of supported image formats for writing.
     * This method delegates the call to the helper component and returns the result.
     *
     * @return a list of supported image format names (e.g., "jpg", "png", "bmp")
     */
    public List<String> allowedFormats() {
        List<String> ext = imgHelp.imgFormats();
        List<String> res = new ArrayList<>();
        ext.forEach(e -> res.add(e.toLowerCase()));
        return res;
    }
}
