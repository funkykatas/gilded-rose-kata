package com.gildedrose.util;

import com.gildedrose.domain.item.Item;

public class ItemFactory {

    public static Item simpleItem(int sellIn, int quality) {
        return new Item("+5 Dexterity Vest", sellIn, quality);
    }

    public static Item brie(int sellIn, int quality) {
        return new Item("Aged Brie", sellIn, quality);
    }

    public static Item sulfuras(int sellIn, int quality) {
        return new Item("Sulfuras, Hand of Ragnaros", sellIn, quality);
    }

    public static Item ticket(int sellIn, int quality) {
        return new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    }

    public static Item conjured(int sellIn, int quality) {
        return new Item("Conjured Mana Cake", sellIn, quality);
    }

}
