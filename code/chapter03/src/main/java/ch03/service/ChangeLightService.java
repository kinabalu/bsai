package ch03.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("ChangeLightService")
@Description("Change a light's state")
public class ChangeLightService implements Function<ChangeLightService.Request, ChangeLightService.Response> {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    LightService lightService;

    public ChangeLightService(LightService lightService) {
        this.lightService = lightService;
    }

    public record Request(String color, boolean on) {
    }

    public record Response(String color, boolean on) {
    }

    public Response apply(Request request) {
        logger.info("Changing status for light: {}", request);
        var light=lightService.setLight(request.color, request.on);
        if(light.isPresent()) {
            return new Response(request.color, request.on);
        } else {
            return null;
        }
    }
}
