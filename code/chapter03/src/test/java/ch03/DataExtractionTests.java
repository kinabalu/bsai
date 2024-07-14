package ch03;

import ch03.service.ConversationChatService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataExtractionTests {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ConversationChatService conversationChatService;

    @Test
    void testDataExtraction() {}
}
