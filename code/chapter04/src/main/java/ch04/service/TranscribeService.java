package ch04.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TranscribeService {
    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;

    public AudioTranscriptionResponse transcribeAudio(Resource audioResource, OpenAiAudioTranscriptionOptions.Builder transcriptionOptions) {
        AudioTranscriptionPrompt transcriptionRequest = transcriptionOptions != null ? new AudioTranscriptionPrompt(audioResource, transcriptionOptions.build()) : new AudioTranscriptionPrompt(audioResource);
        return transcriptionModel.call(transcriptionRequest);
    }
}
