package ch04;

import ch04.service.VoiceAssistantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VirtualAssistantTest {

    @Autowired
    VoiceAssistantService voiceAssistantService;

    @Test
    void runVoiceAssistantCommand() throws IOException {
        byte[] audioResponseBytes = voiceAssistantService.issueCommand("""
                Turn on yellow light.""");

        Files.write(Paths.get("./voice_assistant_response.mp3"), audioResponseBytes);
        assertNotNull(audioResponseBytes);
    }

}
