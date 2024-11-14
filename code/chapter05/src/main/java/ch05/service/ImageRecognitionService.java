package ch05.service;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageRecognitionService {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private OpenAiImageModel openAiImageModel;

    public String identify(String prompt, Media media) {
        var userMessage = new UserMessage(
                prompt,
                media);

        ChatResponse response = chatModel.call(new Prompt(userMessage));

        return response.getResult().getOutput().getContent();
    }
}
