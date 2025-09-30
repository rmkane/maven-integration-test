package org.example.web.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.web.reactive.function.client.WebClient;

import org.example.web.model.Item;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerIT {
    private WebClient webClient;
    private ObjectMapper mapper;

    @BeforeAll
    void beforeAll() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        mapper = new ObjectMapper();
    }

    @Test
    void testGetItems() throws IOException {
        List<Item> items = webClient.get()
                .uri("/api/items")
                .retrieve()
                .bodyToFlux(Item.class) // stream of Item objects
                .collectList() // gather into a List<Item>
                .block(); // block here for a synchronous call

        assertNotNull(items);
        assertFalse(items.isEmpty(), "Expected at least one item");

        writeJSON(items, "items-result.json");
    }

    <T> void writeJSON(T data, String filename) throws IOException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        writeToFile(json, filename);
    }

    void writeToFile(String content, String filename) throws IOException {
        Path targetFile = Path.of("target/integration", filename);
        Files.createDirectories(targetFile.getParent());
        Files.writeString(targetFile, content);
        System.out.println("Wrote content to " + targetFile.toAbsolutePath());
    }
}
