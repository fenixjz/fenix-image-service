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
    <version>1.0.0</version>
</dependency>
```

---

## Methods

### Resize

Resize an image while maintaining its aspect ratio.

#### Method Signature:
```java
public void resize(File inputFile, File outputFile, int targetWidth, int targetHeight)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the resized image will be saved.
- **targetWidth**: Desired width (set `0` to calculate based on height).
- **targetHeight**: Desired height (set `0` to calculate based on width).

#### Example:
```java
fenixImageService.resize(new File("input.jpg"), new File("output.jpg"), 800, 0);
```

---

### Crop

Crop a specific portion of an image based on coordinates and dimensions.

#### Method Signature:
```java
public void crop(File inputFile, File outputFile, int x, int y, int width, int height)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the cropped image will be saved.
- **x**: X-coordinate of the top-left corner.
- **y**: Y-coordinate of the top-left corner.
- **width**: Width of the cropped area.
- **height**: Height of the cropped area.

#### Example:
```java
fenixImageService.crop(new File("input.jpg"), new File("cropped.jpg"), 100, 50, 300, 300);
```

---

### Rotate

Rotate an image by a specified angle around its center.

#### Method Signature:
```java
public void rotate(File inputFile, File outputFile, double angle)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the rotated image will be saved.
- **angle**: Rotation angle in degrees.

#### Example:
```java
fenixImageService.rotate(new File("input.jpg"), new File("rotated.jpg"), 45);
```

---

### Convert

Convert an image from one format to another.

#### Method Signature:
```java
public void convert(File inputFile, File outputFile, String targetFormat)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the converted image will be saved.
- **targetFormat**: The target format (e.g., "jpg", "png").

#### Example:
```java
fenixImageService.convert(new File("input.bmp"), new File("output.png"), "png");
```

---

### Text Watermark

Add a text watermark to an image.

#### Method Signature:
```java
public void textWatermark(File inputFile, File outputFile, String watermarkText, int x, int y, float opacity)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the watermarked image will be saved.
- **watermarkText**: Text to be added as a watermark.
- **x**: X-coordinate of the watermark.
- **y**: Y-coordinate of the watermark.
- **opacity**: Opacity of the watermark (range: 0.0 to 1.0).

#### Example:
```java
fenixImageService.textWatermark(new File("input.jpg"), new File("watermarked.jpg"), "Watermark", 50, 50, 0.5f);
```

---

### Invert Colors

Invert the colors of an image.

#### Method Signature:
```java
public void invert(File inputFile, File outputFile)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the inverted image will be saved.

#### Example:
```java
fenixImageService.invert(new File("input.jpg"), new File("inverted.jpg"));
```

---

### Grayscale

Convert an image to grayscale.

#### Method Signature:
```java
public void grayscale(File inputFile, File outputFile)
```

#### Parameters:
- **inputFile**: The input image file.
- **outputFile**: The output file where the grayscale image will be saved.

#### Example:
```java
fenixImageService.grayscale(new File("input.jpg"), new File("grayscale.jpg"));
```

---

### Allowed Formats

Retrieve a list of supported image formats for writing.

#### Method Signature:
```java
public List<String> allowedFormats()
```

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
            fenixImageService.resize(new File("input.jpg"), new File("resized.jpg"), 800, 600);

            // Crop
            fenixImageService.crop(new File("input.jpg"), new File("cropped.jpg"), 100, 50, 300, 200);

            // Rotate
            fenixImageService.rotate(new File("input.jpg"), new File("rotated.jpg"), 45);

            // Convert
            fenixImageService.convert(new File("input.jpg"), new File("output.png"), "png");

            // Add Watermark
            fenixImageService.textWatermark(new File("input.jpg"), new File("watermarked.jpg"), "Watermark", 50, 50, 0.7f);

            // Invert Colors
            fenixImageService.invert(new File("input.jpg"), new File("inverted.jpg"));

            // Convert to Grayscale
            fenixImageService.grayscale(new File("input.jpg"), new File("grayscale.jpg"));

            // Get Supported Formats
            System.out.println("Supported Formats: " + fenixImageService.allowedFormats());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Feel free to integrate the **Fenix Image Manipulation Service** and enhance your projects with powerful image processing features!
```