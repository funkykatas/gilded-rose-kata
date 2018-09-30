package com.gildedrose.service;

import com.gildedrose.domain.item.Item;
import com.gildedrose.domain.item.ItemRepository;
import com.gildedrose.util.ItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostConstruct
    @SuppressWarnings("unused")
    public void populateStorage() {
        itemRepository.deleteAll();
        itemRepository.saveAll(
                Arrays.asList(
                    ItemFactory.simpleItem(10, 10),
                    ItemFactory.brie(20, 5),
                    ItemFactory.sulfuras(50, 80),
                    ItemFactory.ticket(14, 50),
                    ItemFactory.conjured(20, 40)
                )
        );
    }

    @Scheduled(fixedRateString = "${updateItemsRateMillis}")
    @SuppressWarnings("unused")
    void updateItems() {
        // Most likely wouldn't be using common fork join pool in prod.
        List<Item> updatedItems = getAllItems()
                .parallelStream()
                .map(this::getUpdatedItem)
                .collect(Collectors.toList());
        itemRepository.saveAll(updatedItems);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    private Item getUpdatedItem(Item item) {
        return ItemType.findTypeByName(item.getName()).getUpdatedItem(item);
    }

}
