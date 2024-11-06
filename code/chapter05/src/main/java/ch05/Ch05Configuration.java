package ch05;

import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Ch05Configuration {

//    @Bean
//    OpenAiImageModel imageModel(@Value("${spring.ai.openai.api-key}") String apiKey) {
//        return new OpenAiImageModel(new OpenAiImageApi(apiKey));
//    }

    public static void main(String[] args) {
        SpringApplication.run(Ch05Configuration.class, args);
    }
}
