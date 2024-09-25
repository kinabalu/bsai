package ch03;

import ch03.model.Light;
import ch03.service.LightDataService;
import ch03.service.LightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LightDataTests extends BaseLightTests {
    @Autowired
    LightDataService lightDataService;

    @Test
    void changeLightStatus() {
        var response = lightDataService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights.")
        ));

        var statuses = mapToStatus(response.lights());
        assertTrue(statuses.get("yellow"));
        assertFalse(statuses.get("red"));
        assertFalse(statuses.get("purple"));
    }
}
