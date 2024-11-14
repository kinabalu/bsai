package ch05;

import ch05.service.ImageGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GenerateImageTest {

    @Autowired
    ImageGeneratorService imageGeneratorService;

    @Test
    void runImageGenerationQuery() {
        var image = imageGeneratorService.processPrompt(
                "Can you create an artistic rendering of a bowl full of fruit including only bananas, apples and kiwis",null
        );

        try {
            byte[] binaryData = Base64.getDecoder().decode(image.getB64Json());
            BufferedImage webpImage = ImageIO.read(new ByteArrayInputStream(binaryData));
            WebpToPngConverter.convertWebpToPng(webpImage, "./dall_e_rendering.png");
        } catch (IOException e) {
            System.err.println("IO Error while writing file: " + e.getMessage());
        }
        assertNotNull(image);
    }
}
