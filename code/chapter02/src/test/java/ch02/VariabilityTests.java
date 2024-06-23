package ch02;


import ch02.service.JaccardSimilarityCalculator;
import ch02.service.OptionChatService;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VariabilityTests {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JaccardSimilarityCalculator calculator;
    @Autowired
    OptionChatService optionChatService;

    String query = "Write a short story about a salamander learning to fly.";

    public static Stream<Arguments> controlParameters() {
        return Stream.of(
                Arguments.of(true, 0.0f, 2.0f),
                Arguments.of(false, 0.1f, 1.0f)
        );
    }

    @ParameterizedTest
    @MethodSource("controlParameters")
    void testTemperatures(boolean temp,float lower, float upper) {
        var results = new ArrayList<String>();
        var regression = new SimpleRegression();
        var options = OpenAiChatOptions.builder().build();
        for (float value = lower; value < upper; value += (upper-lower)/5) {
            if(temp) {
                options.setTemperature(value);
            } else {
                options.setTopP(value);
            }
            results.add(optionChatService.query(query, options));
        }
        for(int i=0;i<results.size();i++) {
            log.info("result: {}", results.get(i));
            log.info("----------");
            var c = calculator.calculateJaccardSimilarity(results.getFirst(), results.get(i));
            regression.addData(i,c);
        }

        var slope = regression.getSlope();
        // slope should be negative to indicate less similarity...
        log.info("Slope: {}", slope);
        // but top_p is unpredictable for this.
        if(temp) {
            assertTrue(slope < 0);
        }
    }
}
