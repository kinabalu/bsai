package ch02;

import ch02.service.ConversationChatService;
import com.google.common.collect.Iterables;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ConversationTests {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ConversationChatService conversationChatService;

    List<Message> buildQuery() {
        return List.of(
                new UserMessage("How can I fit a square peg into a round hole?")
        );
    }

    @Test
    void runQuery() {
        var results = conversationChatService.converse(buildQuery());
        System.out.println(results);
    }

    @Test
    void runQuery2() {
        var firstIteration = conversationChatService.converse(buildQuery());
        List<Message> messages = Lists.newArrayList(Iterables.concat(
                List.of(new UserMessage("How can I fit a square peg into a round hole?")),
                firstIteration.stream().map((generation) -> new AssistantMessage(generation.getOutput().getContent())).toList(),
                List.of(new UserMessage("Does it make a difference if it's in the shape of a bird?"))
        ));
        var results = conversationChatService.converse(messages);
        System.out.println(results.getFirst().getOutput().getContent());
        //return results;
    }
}
