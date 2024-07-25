package ch04;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Ch04Configuration {

    @Bean
    OpenAiAudioSpeechModel speechModel(@Value("${spring.ai.openai.api-key}") String apiKey) {
        return new OpenAiAudioSpeechModel(new OpenAiAudioApi(apiKey));
    }

    @Bean
    OpenAiAudioTranscriptionModel transcriptionModel(@Value("${spring.ai.openai.api-key}") String apiKey) {
        return new OpenAiAudioTranscriptionModel(new OpenAiAudioApi(apiKey));
    }

    public static void main(String[] args) {
        SpringApplication.run(Ch04Configuration.class, args);
    }
}
