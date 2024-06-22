package ch02.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient client;

    ChatService(ChatClient.Builder client) {
        this.client = client.build();
    }

    public String query(String query) {
        var response=client
                .prompt(new Prompt(query))
                .call()
                .chatResponse();
        return response.getResult().getOutput().getContent();
    }
}
