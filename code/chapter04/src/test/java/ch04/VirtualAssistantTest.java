package ch04;

import ch04.service.TextToSpeechService;
import ch04.service.TranscribeService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VirtualAssistantTest {

    @Autowired
    TextToSpeechService textToSpeechService;

    @Autowired
    TranscribeService transcribeService;

    @Test
    void runTTSQuery() throws IOException {
        var responseAsBytes = textToSpeechService.processText("""
Chat, returning just a number and the metric what is the approximate average of how far away the moon is from earth in kilometers.
""", null);

        AudioTranscriptionResponse response = transcribeService.transcribeAudio(new ByteArrayResource(responseAsBytes), null);

        String output = response.getResult().getOutput();
        assertTrue(output.contains("384,000 kilometers"));

        assertNotNull(responseAsBytes);
    }

}
