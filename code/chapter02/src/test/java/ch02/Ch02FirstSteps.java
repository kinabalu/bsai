package ch02;

import ch02.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Ch02FirstSteps {
//    @Autowired
//    ChatClient model;

    @Autowired
    ChatService chatService;

    @Test
    void contextLoads() {
        System.out.println(chatService.query("what is the speed of a typical junk carrying tea in November? Assume clear weather and standard currents in the south china sea."));
    }
}
