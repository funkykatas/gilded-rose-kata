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
    public void updateItems() {
        List<Item> items = getAllItems();
        GildedRose gildedRose = new GildedRose(items.toArray(new Item[]{}));
        gildedRose.updateItems();
        itemRepository.saveAll(Arrays.asList(gildedRose.items));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

}
