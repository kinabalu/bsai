package ch02;

import ch02.service.FirstChatService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class Ch02FirstTests {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FirstChatService firstChatService;

    @Test
    void contextLoads() {
        var response = firstChatService.query(
                "what is the speed of a typical junk carrying tea in November?\n" +
                "Assume clear weather and standard currents in the south China sea."
        );
        log.info(response);
        assertTrue(response.toLowerCase().contains("south china sea"));
    }
}
