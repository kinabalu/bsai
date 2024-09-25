package ch03;

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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LightDataService lightDataService;

    private Map<String, Boolean> mapToStatus(LightDataService.Data response) {
        var map = new HashMap<String, Boolean>();
        response.lights().forEach(light -> {
            map.put(light.getColor(), light.isOn());
        });
        return map;
    }

    @Test
    void changeLightStatus() {
        var response = lightDataService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights.")
        ));

        var statuses = mapToStatus(response);
        assertTrue(statuses.get("yellow"));
        assertFalse(statuses.get("red"));
        assertFalse(statuses.get("purple"));
    }

    @Test
    void changeLightStatusWithExtraText() {
        var response = lightDataService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights."),
                new UserMessage("If the light doesn't exist, remove it from the output.")
        ));

        var statuses = mapToStatus(response);
        assertTrue(statuses.get("yellow"));
        assertFalse(statuses.get("red"));
        // note use of null here, not false
        assertNull(statuses.get("purple"));
    }
}
