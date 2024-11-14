package ch05;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class WebpToPngConverter {

    public static byte[] convertWebpToPngByteArray(BufferedImage webpImage) throws IOException {
        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(webpImage, "png", pngOutputStream);

            return pngOutputStream.toByteArray();
        } catch(IOException e) {
            System.err.println("Failed to convert WEBP to PNG: " + e.getMessage());
            throw e;
        }
    }

    public static void convertWebpToPng(BufferedImage webpImage, String pngPath) {
        try {
            ImageIO.write(webpImage, "png", new File(pngPath));

            System.out.println("Conversion complete: " + pngPath);
        } catch (IOException e) {
            System.err.println("Failed to convert WEBP to PNG: " + e.getMessage());
        }
    }

}
