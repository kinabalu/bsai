package ch04;

import ch03.model.Light;
import ch03.service.ChangeLightStatusService;
import ch03.service.LightService;
import ch03.service.RequestLightStatusService;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

@SpringBootApplication
public class Ch04Configuration {
    @Bean
    Light getYellowLight() {
        return new Light("yellow", false);
    }

    @Bean
    Light getRedLight() {
        return new Light("red", false);
    }

    @Bean
    Light getGreenLight() {
        return new Light("green", false);
    }

    @Bean
    Light getBlueLight() {
        return new Light("blue", false);
    }

    @Bean
    LightService getLightService(ApplicationContext context) {
        return new LightService(context);
    }

    @Bean("RequestLightStatusService")
    @Description("Get light status")
    RequestLightStatusService getRequestLightStatusService(LightService lightService) {
        return new RequestLightStatusService(lightService);
    }

    @Bean("ChangeLightStatusService")
    @Description("Change a light's state")
    ChangeLightStatusService getChangeLightStatusService(LightService lightService) {
        return new ChangeLightStatusService(lightService);
    }

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
