package ch04;

import ch04.service.TranscribeService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TranscribeTest {

    @Autowired
    TranscribeService transcribeService;

    @Test
    void transcribeQuery() {
        Resource daisyBellResource = new ClassPathResource("Daisy_Bell_sung_by_DECtalk.flac");
        AudioTranscriptionResponse response = transcribeService.transcribeAudio(daisyBellResource, null);

        assertEquals(response.getResult().getOutput().trim(), "Daisy, Daisy, give me your answer, too. "+
                "I'm half crazy all for the love of you. " +
                "It won't be a stylish marriage. " +
                "I can't afford a carriage. " +
                "But you'd look sweet on the seat of a bicycle built for two.".trim()
        );
    }
}
