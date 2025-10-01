package org.example.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.web.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final List<Item> items;

    public ItemService() {
        this.items = new ArrayList<>();
        this.items.add(new Item(1L, "Widget", "A useful widget"));
        this.items.add(new Item(2L, "Gadget", "A fancy gadget"));
        this.items.add(new Item(3L, "Thingamajig", "An intriguing thingamajig"));
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public Optional<Item> getItemById(Long id) {
        return items.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}


