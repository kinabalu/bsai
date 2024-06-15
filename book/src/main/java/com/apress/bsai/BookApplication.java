package com.apress.bsai;

import com.apress.bsai.service.Publisher;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.ast.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

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
    public CommandLineRunner commandLineRunner(List<String> resourceNames, Asciidoctor asciidoctor, ApplicationContext ctx) {
        return args -> {
            var publishers=resourceNames.stream().sorted().map((name) ->
                    new Publisher(asciidoctor, name, "./target", "generated-content")).toList();
            publishers.forEach(Publisher::processToDocbook);
            publishers.forEach(Publisher::processToDocX);

            System.exit(0);
        };
    }

//    private String buildOutput(Asciidoctor asciidoctor, String name) {
//        System.out.println(name);
//        try (InputStream in = this.getClass().getResourceAsStream("/" + name);
//             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
//            var directory = new File("./target/asciidoctor-output");
//            directory.mkdirs();
//            // TODO correct name to include .xml
//            var outputFile = new File(directory, removeFileExtension(name, false) + ".xml");
//            var writer = new FileWriter(outputFile);
//            var options = Options.builder()
//                    .compact(true)
//                    .backend("docbook")
//                    .build();
//            asciidoctor.convert(reader, writer, options);
//            return outputFile.getAbsolutePath();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String readContent(String name) {
//        try (InputStream in = this.getClass().getResourceAsStream("/" + name)) {
//            var doc = IOUtils.readLines(Objects.requireNonNull(in), StandardCharsets.UTF_8);
//            return String.join("\n", doc);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
