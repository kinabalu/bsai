package com.apress.bsai.service;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class Publisher {
    private final Asciidoctor asciidoctor;
    private final String fileName;
    private final File workDir;
    private final File targetDir;
    private final String baseName;
    private String workingName;

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

        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }

    public void processToDocbook() {
        try (InputStream in = this.getClass().getResourceAsStream("/" + fileName);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            workingName = baseName + ".xml";
            var outputFile = new File(targetDir, workingName);
            var writer = new FileWriter(outputFile);
            var options = Options.builder()
                    .compact(true)
                    .backend("docbook")
                    .build();
            asciidoctor.convert(reader, writer, options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processToDocX() {
        File input=new File(targetDir, workingName);
        new File("./target/content").mkdirs();
        var command = new String[]{"pandoc", "-r", "docbook", "-t", "docx", "-o", "./target/content/" + baseName + ".docx", "--lua-filter", "src/main/resources/lua/admonitions.lua", input.getPath()};
        System.out.println(Arrays.toString(command));
        try {
            String output=new ProcessExecutor().command(command).readOutput(true).execute().outputUTF8();
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
