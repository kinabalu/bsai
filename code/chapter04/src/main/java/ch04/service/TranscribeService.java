package ch04.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TranscribeService {

    @Value("${spring.ai.openai.api-key}") private String apiKey;

    private OpenAiAudioTranscriptionModel transcriptionModel;

    @PostConstruct
    public void init() {
        var openAiAudioApi = new OpenAiAudioApi(apiKey);

        transcriptionModel = new OpenAiAudioTranscriptionModel(openAiAudioApi);
    }

    public AudioTranscriptionResponse transcribeAudio(Resource audioResource) {
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();

        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioResource, transcriptionOptions);
        return transcriptionModel.call(transcriptionRequest);
    }
}
