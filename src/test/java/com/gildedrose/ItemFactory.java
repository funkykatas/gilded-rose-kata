package com.gildedrose;

class ItemFactory {

    static Item simpleItem(int sellIn, int quality) {
        return new Item("+5 Dexterity Vest", sellIn, quality);
    }

    static Item brie(int sellIn, int quality) {
        return new Item("Aged Brie", sellIn, quality);
    }

    static Item sulfuras(int sellIn, int quality) {
        return new Item("Sulfuras, Hand of Ragnaros", sellIn, quality);
    }

    static Item ticket(int sellIn, int quality) {
        return new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    }

    static Item conjured(int sellIn, int quality) {
        return new Item("Conjured Mana Cake", sellIn, quality);
    }

}
