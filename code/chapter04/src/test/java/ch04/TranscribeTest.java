package ch04;

import ch04.service.TranscribeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class TranscribeTest {

    @Autowired
    TranscribeService transcribeService;

    @Test
    void transcribeQuery() {
        //
    }
}
