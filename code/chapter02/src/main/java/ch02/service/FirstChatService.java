package ch02.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class FirstChatService {
    private final ChatClient client;

    FirstChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public String query(String query) {
        var prompt = new Prompt(query);

        var request = client
                .prompt(prompt);

        var responseSpecification = request.call();

        return responseSpecification.content();
    }
}
