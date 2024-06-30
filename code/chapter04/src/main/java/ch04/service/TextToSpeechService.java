package ch04.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    @Value("${spring.ai.openai.api-key}") private String apiKey;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private OpenAiAudioSpeechOptions speechOptions;

    private OpenAiAudioSpeechModel speechModel;

    public byte[] processText(String text) {
        var openAiAudioApi = new OpenAiAudioApi(apiKey);

        speechModel = new OpenAiAudioSpeechModel(openAiAudioApi);

        speechOptions = OpenAiAudioSpeechOptions.builder()
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withSpeed(1.0f)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        var speechPrompt = new SpeechPrompt(text, speechOptions);
        SpeechResponse response = speechModel.call(speechPrompt);
        return response.getResult().getOutput();
    }
}
