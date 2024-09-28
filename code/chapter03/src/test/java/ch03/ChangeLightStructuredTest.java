package ch03;

import ch03.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChangeLightStructuredTest extends BaseLightTests {
    @Autowired
    LightStructuredUpdateService service;

    LightStructuredUpdateService.LightWithXYZ find(
            String color,
            List<LightStructuredUpdateService.LightWithXYZ> lights
    ) {
        return lights
                .stream()
                .filter(i -> i.color().equalsIgnoreCase(color))
                .findFirst()
                .orElseThrow();
    }

    @Test
    void changeLightStatus() {
        // we use the actual types here for clarity only.
        // var would have worked fine.
        LightStructuredUpdateService.LightWithXYZList response =
                service.converse(List.of(
                        new UserMessage("Turn the yellow light on. " +
                                "Then show the state of the " +
                                "red, green, blue, purple, and yellow lights.")
                ));

        LightStructuredUpdateService.LightWithXYZ yellow =
                find("yellow", response.lights());
        assertTrue(yellow.on());
        assertEquals(0.4447, yellow.x(), 0.01);
        assertEquals(0.5153, yellow.y(), 0.01);
        assertEquals(0.04, yellow.z(), 0.01);

        LightStructuredUpdateService.LightWithXYZ purple =
                find("purple", response.lights());
        assertNull(purple.x());
        assertNull(purple.y());
        assertNull(purple.z());
    }
}
