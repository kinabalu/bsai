package ch04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ch03.service.LightService;

@Service
public class VoiceAssistantService {
    @Autowired
    ApplicationContext context;
    
    @Autowired
    private LightService lightService;

    @Autowired
    private TranscribeService transcribeService;

    @Autowired
    private TextToSpeechService textToSpeechService;

}
