# Fenix Image Manipulation Service

**Fenix Image Manipulation Service** is a Java library for image manipulation, providing functionalities such as resizing, cropping, rotating, format conversion, watermarking, color inversion, and grayscale conversion. This library is designed to be integrated into other services via [JitPack](https://jitpack.io).

---

## Table of Contents

- [Installation](#installation)
- [Methods](#methods)
  - [Resize](#resize)
  - [Crop](#crop)
  - [Rotate](#rotate)
  - [Convert](#convert)
  - [Text Watermark](#text-watermark)
  - [Invert Colors](#invert-colors)
  - [Grayscale](#grayscale)
  - [Allowed Formats](#allowed-formats)
- [Usage Example](#usage-example)

---

## Installation

To integrate **Fenix Image Manipulation Service** into your project using **JitPack**:

### Step 1: Add the JitPack Repository

Add the following to your `pom.xml` file:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

### Step 2: Add the Dependency

Include the dependency for this library:

```xml
<dependency>
  <groupId>com.github.fenixjz</groupId>
  <artifactId>fenix-image-service</artifactId>
  <version>1.0.1</version>
</dependency>
```

---

## Methods

### Resize

Resize an image while maintaining its aspect ratio.

#### Method Signature:
```java
public String resize(File inputFile, File outputFile, int targetWidth, int targetHeight)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the resized image will be saved.
- **targetWidth**: Desired width (set `0` to calculate based on height).
- **targetHeight**: Desired height (set `0` to calculate based on width).

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.resize(new File("input.jpg"), new File("output.jpg"), 800, 0);
System.out.println("Resized image saved at: " + outputPath);
```

---

### Crop

Crop a specific portion of an image based on coordinates and dimensions.

#### Method Signature:
```java
public String crop(File inputFile, File outputFile, int x, int y, int width, int height)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the cropped image will be saved.
- **x**: X-coordinate of the top-left corner.
- **y**: Y-coordinate of the top-left corner.
- **width**: Width of the cropped area.
- **height**: Height of the cropped area.

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.crop(new File("input.jpg"), new File("cropped.jpg"), 100, 50, 300, 300);
System.out.println("Cropped image saved at: " + outputPath);
```

---

### Rotate

Rotate an image by a specified angle around its center.

#### Method Signature:
```java
public String rotate(File inputFile, File outputFile, double angle)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the rotated image will be saved.
- **angle**: Rotation angle in degrees.

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.rotate(new File("input.jpg"), new File("rotated.jpg"), 45);
System.out.println("Rotated image saved at: " + outputPath);
```

---

### Convert

Convert an image from one format to another.

#### Method Signature:
```java
public String convert(File inputFile, File outputFile, String targetFormat)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the converted image will be saved.
- **targetFormat**: The target format (e.g., "jpg", "png").

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.convert(new File("input.bmp"), new File("output.png"), "png");
System.out.println("Converted image saved at: " + outputPath);
```

---

### Text Watermark

Add a text watermark to an image.

#### Method Signature:
```java
public String textWatermark(File inputFile, File outputFile, String watermarkText, int x, int y, float opacity)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the watermarked image will be saved.
- **watermarkText**: Text to be added as a watermark.
- **x**: X-coordinate of the watermark.
- **y**: Y-coordinate of the watermark.
- **opacity**: Opacity of the watermark (range: 0.0 to 1.0).

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.textWatermark(new File("input.jpg"), new File("watermarked.jpg"), "Watermark", 50, 50, 0.5f);
System.out.println("Watermarked image saved at: " + outputPath);
```

---

### Invert Colors

Invert the colors of an image.

#### Method Signature:
```java
public String invert(File inputFile, File outputFile)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the inverted image will be saved.

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.invert(new File("input.jpg"), new File("inverted.jpg"));
System.out.println("Inverted image saved at: " + outputPath);
```

---

### Grayscale

Convert an image to grayscale.

#### Method Signature:
```java
public String grayscale(File inputFile, File outputFile)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the grayscale image will be saved.

#### Returns:
- **String**: The absolute path of the output file.

#### Example:
```java
String outputPath = fenixImageService.grayscale(new File("input.jpg"), new File("grayscale.jpg"));
System.out.println("Grayscale image saved at: " + outputPath);
```

---

### Allowed Formats

Retrieve a list of supported image formats for writing.

#### Method Signature:
```java
public List<String> allowedFormats()
```

#### Returns:
- **List<String>**: A list of supported image format names (e.g., "jpg", "png", "bmp").

#### Example:
```java
List<String> formats = fenixImageService.allowedFormats();
System.out.println("Supported formats: " + formats);
```

---

## Usage Example

Below is an example of how to use `FenixImageService` in your application:

```java
import com.fenix.fenix_image_service.service.FenixImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class ImageProcessor {

    @Autowired
    private FenixImageService fenixImageService;

    public void processImages() {
        try {
            // Resize
            String resizedPath = fenixImageService.resize(new File("input.jpg"), new File("resized.jpg"), 800, 600);
            System.out.println("Resized image saved at: " + resizedPath);

            // Crop
            String croppedPath = fenixImageService.crop(new File("input.jpg"), new File("cropped.jpg"), 100, 50, 300, 200);
            System.out.println("Cropped image saved at: " + croppedPath);

            // Rotate
            String rotatedPath = fenixImageService.rotate(new File("input.jpg"), new File("rotated.jpg"), 45);
            System.out.println("Rotated image saved at: " + rotatedPath);

            // Convert
            String convertedPath = fenixImageService.convert(new File("input.jpg"), new File("output.png"), "png");
            System.out.println("Converted image saved at: " + convertedPath);

            // Add Watermark
            String watermarkedPath = fenixImageService.textWatermark(new File("input.jpg"), new File("watermarked.jpg"), "Watermark", 50, 50, 0.7f);
            System.out.println("Watermarked image saved at: " + watermarkedPath);

            // Invert Colors
            String invertedPath = fenixImageService.invert(new File("input.jpg"), new File("inverted.jpg"));
            System.out.println("Inverted image saved at: " + invertedPath);

            // Convert to Grayscale
            String grayscalePath = fenixImageService.grayscale(new File("input.jpg"), new File("grayscale.jpg"));
            System.out.println("Grayscale image saved at: " + grayscalePath);

            // Get Supported Formats
            List<String> formats = fenixImageService.allowedFormats();
            System.out.println("Supported formats: " + formats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Feel free to integrate the **Fenix Image Manipulation Service** and enhance your projects with powerful image processing features!

