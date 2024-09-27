package ch03.service;

import ch03.model.Light;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LightStructuredUpdateService {
    private final ChatClient client;

    public record Data(List<Light> lights) {
    }

    LightStructuredUpdateService(ChatClient.Builder builder) {
        client = builder.build();
    }

    public Data converse(List<Message> messages) {
        return converse(messages,
                new OpenAiChatOptions
                        .Builder()
                        .withFunction("RequestLightStatusService")
                        .withFunction("ChangeLightService")
                        .build());
    }

    public Data converse(
            List<Message> messages,
            OpenAiChatOptions options
    ) {
        //var prompt = new Prompt(messages, options);
        return client
                .prompt()
                .messages(messages)
                .options(options)
                .call()
                .entity(Data.class);
    }
}
