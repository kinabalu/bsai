package ch03.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("RequestLightStatusService")
@Description("Get light status")
public class RequestLightStatusService implements Function<RequestLightStatusService.Request, RequestLightStatusService.Response> {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    LightService lightService;

    public RequestLightStatusService(LightService lightService) {
        this.lightService = lightService;
    }

    public record Request(String color, boolean on) {
    }

    public record Response(String color, boolean on) {
    }

    public Response apply(Request request) {
        logger.info("Requesting status for light: {}", request);
        var light = lightService.getLight(request.color);
        if (light.isPresent()) {
            return new Response(request.color, request.on);
        } else {
            return null;
        }
    }
}