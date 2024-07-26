package ch03;

import ch03.service.ConversationChatService;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class DataExtractionTests {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ConversationChatService conversationChatService;

    /**
     * This method extracts the `AssistantMessage` from the generated LLM output
     *
     * @param output the results of the call to the LLM
     * @return the first `AssistantMessage` in the output
     */
    private AssistantMessage getAssistantMessage(List<Generation> output) {
        return output.getFirst().getOutput();
    }

    /**
     * This method simply wraps the content and dumps it to a logger.
     *
     * @param content A string to display
     */
    private void display(String content) {
        var lines = WordUtils
                .wrap(content, 62, "\n", true)
                .split("\\n");
        for (String line : lines) {
            log.info(line);
        }
        log.info("-----");
    }

    @Test
    void testDataExtraction() {
        BeanOutputConverter<DoublePair> beanOutputConverter =
                new BeanOutputConverter<>(DoublePair.class);
var format=beanOutputConverter.getFormat();
var template="What is the slope of y=x*1.2/(z*w) if z=2? Select w based on the current second of the current minute.";
Prompt prompt=new Prompt(new PromptTemplate(template, Map.of("format", format)).createMessage());
var response =conversationChatService.client.prompt(prompt).call().chatResponse();
var message=getAssistantMessage(response.getResults());
display(message.getContent());
//        var messages = new ArrayList<Message>();
//        messages.add( new UserMessage("What is the slope of y=x*1.2/(z*2) if z=2 and w is a random number?"));


    }
}
