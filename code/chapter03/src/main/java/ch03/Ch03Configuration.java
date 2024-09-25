package ch03;

import ch03.model.Light;
import ch03.service.ChangeLightService;
import ch03.service.LightService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@SpringBootApplication
public class Ch03Configuration {
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

    @Bean
    @Description("set the status of a light") // function description
    public Function<ChangeLightService.Request, ChangeLightService.Response> setLight(LightService lightService) {
        return new ChangeLightService(lightService);
    }
}
