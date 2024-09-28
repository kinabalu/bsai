package ch04.service;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TranscribeService {
    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;

    public AudioTranscriptionResponse transcribeAudio(Resource audioResource, OpenAiAudioTranscriptionOptions.Builder transcriptionOptions) {
        var transcriptionRequest = transcriptionOptions != null ? new AudioTranscriptionPrompt(audioResource, transcriptionOptions.build()) : new AudioTranscriptionPrompt(audioResource);
        return transcriptionModel.call(transcriptionRequest);
    }
}
