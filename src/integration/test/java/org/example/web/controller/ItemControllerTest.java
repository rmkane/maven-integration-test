package org.example.web.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.web.reactive.function.client.WebClient;

import org.example.web.model.Item;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerTest {
    private WebClient webClient;
    private TestUtils utils;

    @BeforeAll
    void beforeAll() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
        utils = new TestUtils(new ObjectMapper());
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

        utils.writeJSON(items, "items-result.json");
    }
}
