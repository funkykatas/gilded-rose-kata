package com.gildedrose.domain.item;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Item {

    @Id
    private final String name;
    private final int sellIn;
    private final int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return sellIn == item.sellIn &&
                quality == item.quality &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sellIn, quality);
    }

    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public int getQuality() {
        return quality;
    }
}
