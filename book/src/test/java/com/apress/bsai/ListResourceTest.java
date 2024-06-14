package com.apress.bsai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ListResourceTest {
    @Autowired
    List<String> resources;

    @Test
    public void testListResource() {
        System.out.println(resources);
    }
}
