package ch04.service;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class TranscribeService {

    @Value("${spring.ai.openai.api-key}") private String apiKey;

    private OpenAiAudioTranscriptionModel transcriptionModel;

    public TranscribeService() {
        var openAiAudioApi = new OpenAiAudioApi(apiKey);

        transcriptionModel = new OpenAiAudioTranscriptionModel(openAiAudioApi);
    }

    public AudioTranscriptionResponse transcribeAudio(FileSystemResource audioFile) {
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();

//        var audioFile = new FileSystemResource("/path/to/your/resource/speech/jfk.flac");

        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
        return response;
    }
}
