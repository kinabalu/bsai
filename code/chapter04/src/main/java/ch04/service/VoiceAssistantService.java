package ch04.service;

import ch03.service.UpdateChatService;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoiceAssistantService {

    @Autowired
    private UpdateChatService updateChatService;

    @Autowired
    private TranscribeService transcribeService;

    @Autowired
    private TextToSpeechService textToSpeechService;

    public byte[] issueCommand(Resource capturedAudio) {
        AudioTranscriptionResponse response = transcribeService.transcribeAudio(capturedAudio, null);

        String output = response.getResult().getOutput();
        var updateResponse = updateChatService.converse(
                List.of(
                        new UserMessage(output)
                )
        );

        return textToSpeechService.processText(updateResponse.getFirst().getOutput().getContent(), null);
    }

    public byte[] issueCommand(String commandText) {
        var responseAsBytes = textToSpeechService.processText(commandText, null);

        return this.issueCommand(new ByteArrayResource(responseAsBytes));
    }
}
