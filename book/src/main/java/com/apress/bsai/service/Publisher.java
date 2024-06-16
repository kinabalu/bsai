package com.apress.bsai.service;

import org.apache.commons.io.IOUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.ast.Document;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class Publisher {
    static int totalWordCount=0;
    private final Asciidoctor asciidoctor;
    private final String fileName;
    private final File workDir;
    private final File targetDir;
    private final String baseName;
    private String workingName;
    private Document document;

    public Publisher(Asciidoctor asciidoctor, String fileName, String workDir, String targetDir) {
        this.asciidoctor = asciidoctor;
        this.fileName = fileName;
        this.workDir = new File(workDir);
        this.targetDir = new File(workDir, targetDir);
        this.targetDir.mkdirs();
        this.baseName = removeFileExtension(fileName, false);
    }

    // appreciate it, baeldung
    public String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }

        var extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }

    public Document buildDocument() {
        try (var in = this.getClass().getResourceAsStream("/" + fileName);
             var reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            var content = String.join("\n", IOUtils.readLines(reader));
            var options = Options.builder()
                    .compact(true)
                    .backend("docbook")
                    .build();
            document = asciidoctor.load(content, options);

            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processToDocbook() {
        workingName = baseName + ".xml";
        var outputFile = new File(targetDir, workingName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputFile);
            IOUtils.write(document.convert(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // need to get word count
        // need to
    }

    synchronized public int getWordCount() {
        int wordCount=0;
        // TODO implement this!


        // each block has a bunch of nodes. Convert to text and count words, eh.

        totalWordCount+=wordCount;
        return wordCount;
    }

    public int getFleschKincaid() {
        // TODO implement
        return 0;
    }

    public void processToDocX() {
        var input = new File(targetDir, workingName);
        new File("./target/content").mkdirs();
        var command = new String[]{"pandoc", "-r", "docbook", "-t", "docx", "-o", "./target/content/" + baseName + ".docx", "--lua-filter", "src/main/resources/lua/admonitions.lua", input.getPath()};
        System.out.println(Arrays.toString(command));
        try {
            var output = new ProcessExecutor().command(command).readOutput(true).execute().outputUTF8();
            System.out.println(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
