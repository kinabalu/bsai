package ch04;

import ch04.service.TextToSpeechService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpeechTTSTest {

    @Autowired
    TextToSpeechService textToSpeechService;

    @Test
    void runTTSQuery() throws IOException {
        var responseAsBytes = textToSpeechService.processText("I'm a little teapot short and stout");

        Files.write(Paths.get("./teapot.mp3"), responseAsBytes);
        assertNotNull(responseAsBytes);
    }

}
