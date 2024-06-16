package com.apress.bsai.service;

import org.asciidoctor.Asciidoctor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@ShellComponent
public class OperationsService {
    private final Asciidoctor asciidoctor;
    private final List<String> resourceNames;

    public OperationsService(Asciidoctor asciidoctor, List<String> resourceNames) {
        this.asciidoctor=asciidoctor;
        this.resourceNames=resourceNames;
    }
    @ShellMethod("analyze")
    void analyze() {
        var publishers=resourceNames.stream().sorted().map((name) ->
                new Publisher(asciidoctor, name, "./target", "generated-content")).toList();
        publishers.forEach(Publisher::buildDocument);
        publishers.forEach(Publisher::getWordCount);
        publishers.forEach(Publisher::processToDocbook);
        publishers.forEach(Publisher::processToDocX);
    }

}
