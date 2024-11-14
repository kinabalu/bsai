package ch05;

import ch03.service.UpdateChatService;
import ch05.service.ImageGeneratorService;
import ch05.service.ImageRecognitionService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.image.Image;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LightVisualizerTest {
    @Autowired
    ImageGeneratorService generatorService;

    @Autowired
    ImageRecognitionService recognitionService;

    @Autowired
    private UpdateChatService updateChatService;

    String runCommand(String prompt) {
        var updateResponse = updateChatService.converse(
                List.of(
                        new UserMessage(prompt)
                )
        );

        return updateResponse.getFirst().getOutput().getContent();
    }

    Media imageToMedia(Image image) throws IOException {
        byte[] binaryData = Base64.getDecoder().decode(image.getB64Json());
        BufferedImage webpImage = ImageIO.read(new ByteArrayInputStream(binaryData));
        byte[] pngData = WebpToPngConverter.convertWebpToPngByteArray(webpImage);
        return new Media(MimeTypeUtils.IMAGE_PNG, new ByteArrayResource(pngData));
    }

    @Test
    void runLightVisualizer() {
        try {
            var initialLightbulbPrompt = runCommand("Based on the status of the lights red, yellow and green create a single sentence prompt for DALL-E of an realistic rendering of a lightbulb for any color which is turned on");
            var image = generatorService.processPrompt(
                    initialLightbulbPrompt,null
            );

            Media firstLightbulbMedia = imageToMedia(image);
            String recognition = recognitionService.identify(
                    "In a single sentence explain what is in this picture?",
                    firstLightbulbMedia);

            assertTrue(recognition.toLowerCase().contains("red"));

            runCommand("Turn off red light and turn on green light");

            var newLightBulbRenderPrompt = runCommand("Based on the status of the lights red, yellow and green create a single sentence prompt for DALL-E of an realistic rendering of a lightbulb for any color which is turned on");

            var newLightBulbImage = generatorService.processPrompt(
                    newLightBulbRenderPrompt,null
            );

            Media secondLightbulbMedia = imageToMedia(newLightBulbImage);
            String newRecognition = recognitionService.identify(
                    "In a single sentence explain what is in this picture?",
                    secondLightbulbMedia);

            assertTrue(newRecognition.toLowerCase().contains("green"));
        } catch (IOException e) {
            System.err.println("IO Error while writing file: " + e.getMessage());
        }

    }
}
