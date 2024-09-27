package ch03;

import ch03.service.LightStructuredUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LightDataTests extends BaseLightTests {
    @Autowired
    LightStructuredUpdateService lightStructuredUpdateService;

    @Test
    void changeLightStatus() {
        var response = lightStructuredUpdateService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights.")
        ));

        var statuses = mapToStatus(response.lights());
        assertTrue(statuses.get("yellow"));
        assertFalse(statuses.get("red"));
        assertFalse(statuses.get("purple"));
    }
}
