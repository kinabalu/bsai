package com.apress.bsai;

import com.apress.bsai.service.OperationsService;
import org.asciidoctor.Asciidoctor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BookApplication {
    @Bean
    Asciidoctor getAsciiDoctor() {
        return Asciidoctor.Factory.create();
    }

    @Bean
    List<String> getResources() throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] mappingLocations = patternResolver.getResources("classpath*:/*.adoc");

        return Arrays.stream(mappingLocations).map(Resource::getFilename).toList();
    }

    @Bean
    public OperationsService getOperationsService(Asciidoctor asciidoctor, List<String> resources) {
        return new OperationsService(asciidoctor, resources);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
