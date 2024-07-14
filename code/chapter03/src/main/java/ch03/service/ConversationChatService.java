package ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationChatService {
    protected final ChatClient client;

    ConversationChatService(ChatClient.Builder builder) {
        client = builder.build();
    }

    public List<Generation> converse(List<Message> messages) {
        return converse(messages, new OpenAiChatOptions.Builder().build());
    }

    public List<Generation> converse(
            List<Message> messages,
            OpenAiChatOptions options
    ) {
        var prompt = new Prompt(messages, options);
        return client.prompt(prompt).call().chatResponse().getResults();
    }
}
