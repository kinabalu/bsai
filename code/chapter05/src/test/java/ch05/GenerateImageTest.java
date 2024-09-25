package ch05;

import ch05.service.ImageGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GenerateImageTest {

    @Autowired
    ImageGeneratorService imageGeneratorService;

    @Test
    void runImageGenerationQuery() {
        var image = imageGeneratorService.processPrompt("an astronaut riding a horse, by Hiroshige",null);

        System.out.println(image.getUrl());
        System.out.println(image.getB64Json());
        assertNotNull(image);
    }
}
