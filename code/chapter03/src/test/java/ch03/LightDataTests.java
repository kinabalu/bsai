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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LightDataTests extends BaseLightTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LightDataService lightDataService;

    @Test
    void changeLightStatus() {
        var response = lightDataService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights.")
        ));
        logger.info("response: {}", response);

        assertState("yellow", true);
        assertState("red", false);
    }

    @Test
    void changeLightStatusWithExtraText() {
        var response = lightDataService.converse(List.of(
                new UserMessage("Turn the yellow light on."),
                new UserMessage("Then show the state of the red, green, blue, purple, and yellow lights."),
                new UserMessage("If the light doesn't exist, remove it from the output.")
        ));
        logger.info("response: {}", response);

        assertState("yellow", true);
        assertState("red", false);
    }
}
