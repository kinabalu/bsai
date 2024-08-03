package ch05.service;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageGeneratorService {

    @Autowired
    OpenAiImageModel imageModel;

    public Image processPrompt(String prompt, OpenAiImageOptions.Builder imageOptions) {
        var imagePrompt = imageOptions != null ? new ImagePrompt(prompt, imageOptions.build()) : new ImagePrompt(prompt);
        ImageResponse response = imageModel.call(imagePrompt);
        return response.getResult().getOutput();
    }
}
