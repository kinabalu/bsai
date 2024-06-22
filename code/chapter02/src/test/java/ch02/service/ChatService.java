package ch02.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient client;

    ChatService(ChatClient.Builder client) {
        this.client = client.build();
    }

    public String query(String query) {
        return client.prompt(new Prompt(query)).call().content();
    }
}
