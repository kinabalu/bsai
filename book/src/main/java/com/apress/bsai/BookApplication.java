package com.apress.bsai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class BookApplication {

    @Bean
    List<String> getResources() throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] mappingLocations = patternResolver.getResources("classpath*:/*.adoc");

        return Arrays.stream(mappingLocations).map(Resource::getFilename).toList();
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
