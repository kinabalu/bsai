package ch03;

import ch03.service.LightDataService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CleanedLightDataTests extends BaseLightTests {
    @Autowired
    LightDataService lightDataService;

    @Test
    void changeLightStatusWithExtraText() {
        var response = lightDataService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights."),
                new UserMessage("If the light doesn't exist, remove it from the output.")
        ));

        var statuses = mapToStatus(response.lights());
        assertTrue(statuses.get("yellow"));
        assertFalse(statuses.get("red"));
        // note use of null here, not false
        assertNull(statuses.get("purple"));
    }
}
