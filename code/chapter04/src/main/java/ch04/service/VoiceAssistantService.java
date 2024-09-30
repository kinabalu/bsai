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
    @Bean
    LightService getLightService() { return new LightService(context); }

    @Autowired
    private TranscribeService transcribeService;

    @Autowired
    private TextToSpeechService textToSpeechService;

}
