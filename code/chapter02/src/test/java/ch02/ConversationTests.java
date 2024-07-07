package ch02;

import ch02.service.ConversationChatService;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConversationTests {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ConversationChatService conversationChatService;

    private List<Generation> display(List<Generation> results) {
        var content = results.getFirst().getOutput().getContent();
        Arrays.stream(
                        WordUtils
                                .wrap(content, 62, "\n", true)
                                .split("\\n")
                )
                .forEach(log::info);
        log.info("-----");
        return results;
    }

    @Test
    @Order(1)
    void runQuery() {
        display(conversationChatService.converse(List.of(
                new UserMessage("How can I fit a square peg into a round hole?")
        )));
    }

    @Test
    @Order(2)
    void runQuery2() {
        // we want to make a mutable list, because we're adding context.
        List<Message> messages = new ArrayList<>(List.of(
                new UserMessage("How can I fit a square peg into a round hole?")
        ));
        var results = display(conversationChatService.converse(messages));

        // we want to establish the context of the first answer.
        messages.add(results.getFirst().getOutput());
        // now we want to add some extra context of our own...
        messages.add(
                new UserMessage("Does it make a difference if it's in the shape of a bird?")
        );
        display(conversationChatService.converse(messages));
    }
}
