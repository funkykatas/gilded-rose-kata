package com.gildedrose.service;

import com.gildedrose.domain.item.Item;

import java.util.Arrays;

class GildedRose {

    Item[] items;

    GildedRose(Item[] items) {
        this.items = items;
    }

    void updateItems() {
        Arrays.stream(items).forEach(i -> ItemType.findTypeByName(i.name).updateItem(i));
    }

}