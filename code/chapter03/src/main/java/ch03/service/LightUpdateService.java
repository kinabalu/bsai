package ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class LightUpdateService extends LightQueryService {
    LightUpdateService(ChatClient.Builder builder) {
        super(builder);
    }

    @Override
    public OpenAiChatOptions buildOptions() {
        return new OpenAiChatOptions
                .Builder()
                .withFunction("RequestLightStatusService")
                .withFunction("ChangeLightService")
                .build();
    }
}
