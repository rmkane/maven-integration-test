package org.example.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUtils {
    private final ObjectMapper mapper;

    public <T> void writeJSON(T data, String filename) throws IOException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        writeToFile(json, filename);
    }

    public void writeToFile(String content, String filename) throws IOException {
        Path targetFile = Path.of("target/integration", filename);
        Files.createDirectories(targetFile.getParent());
        Files.writeString(targetFile, content);
        System.out.println("Wrote content to " + targetFile.toAbsolutePath());
    }
}
