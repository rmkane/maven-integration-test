package org.example.web.service;

import org.example.web.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService();
    }

    @Test
    void getAllItems_returnsThreeMockItems_andIsIndependentCopy() {
        List<Item> firstCall = itemService.getAllItems();
        assertEquals(3, firstCall.size());

        // Modify returned list to ensure service maintains its internal state
        firstCall.add(new Item(99L, "Extra", "Should not persist"));

        List<Item> secondCall = itemService.getAllItems();
        assertEquals(3, secondCall.size());

        // Basic content sanity checks
        boolean hasWidget = secondCall.stream().anyMatch(i -> i.getName().equals("Widget"));
        boolean hasGadget = secondCall.stream().anyMatch(i -> i.getName().equals("Gadget"));
        boolean hasThing = secondCall.stream().anyMatch(i -> i.getName().contains("Thingamajig"));
        assertTrue(hasWidget && hasGadget && hasThing);
    }

    @Test
    void getItemById_returnsPresentForExisting_andEmptyForMissing() {
        Optional<Item> gadget = itemService.getItemById(2L);
        assertTrue(gadget.isPresent());
        assertEquals(2L, gadget.get().getId());
        assertEquals("Gadget", gadget.get().getName());

        Optional<Item> missing = itemService.getItemById(42L);
        assertTrue(missing.isEmpty());
    }
}


