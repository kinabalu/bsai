package ch04.service;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    private final OpenAiAudioSpeechModel speechModel;

    @Autowired
    public TextToSpeechService(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    public byte[] processText(String text, OpenAiAudioSpeechOptions.Builder speechOptions) {
        var speechPrompt = speechOptions != null ? new SpeechPrompt(text, speechOptions.build()) : new SpeechPrompt(text);
        SpeechResponse response = speechModel.call(speechPrompt);
        return response.getResult().getOutput();
    }
}
