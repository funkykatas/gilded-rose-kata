package com.gildedrose.service;

import com.gildedrose.domain.item.Item;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum ItemType {

    SIMPLE   (ItemNames.SIMPLE_ITEM_NAME, ItemType::updateSellInSimple,   ItemType::updateQualitySimple),
    BRIE     (ItemNames.BRIE_NAME,        ItemType::updateSellInSimple,   ItemType::updateQualityBrie),
    SULFURAS (ItemNames.SULFURAS_NAME,    ItemType::updateSellInSulfuras, ItemType::updateQualitySulfuras),
    TICKET   (ItemNames.TICKET_NAME,      ItemType::updateSellInSimple,   ItemType::updateQualityTicket),
    CONJURED (ItemNames.CONJURED_NAME,    ItemType::updateSellInSimple,   ItemType::updateQualityConjured);

    private static final int MAX_QUALITY = 50;
    private static final int SULFURAS_QUALITY = 80;
    private static final int QUALITY_CHANGE_STEP = 1;
    private static final int QUALITY_CHANGE_DOUBLE_STEP = 2;
    private static final int MAX_TICKET_QUALITY_CHANGE_STEP = 3;
    private static final int CONJURED_ITEM_DEGRADE_RATE = 2;
    private static final int TICKET_SELL_IN_DAYS_STEP_1 = 10;
    private static final int TICKET_SELL_IN_DAYS_STEP_2 = 5;
    private static final int MIN_QUALITY = 0;

    static class ItemNames {
        private static final String SIMPLE_ITEM_NAME = "";
        private static final String BRIE_NAME = "Aged Brie";
        private static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";
        private static final String TICKET_NAME = "Backstage passes to a TAFKAL80ETC concert";
        private static final String CONJURED_NAME = "Conjured Mana Cake";
    }

    private final String name;
    private final Function<Integer, Integer> updateSellInFunction;
    private final BiFunction<Integer, Integer, Integer> updateQualityFunction;

    ItemType(String name,
             Function<Integer, Integer> updateSellInFunction,
             BiFunction<Integer, Integer, Integer> updateQualityFunction) {

        this.name = name;
        this.updateSellInFunction = updateSellInFunction;
        this.updateQualityFunction = updateQualityFunction;
    }

    Item getUpdatedItem(Item item) {
        int quality = updateQualityFunction.apply(item.getQuality(), item.getSellIn());
        int sellIn = updateSellInFunction.apply(item.getSellIn());
        return new Item(item.getName(), sellIn, quality);
    }

    static ItemType findTypeByName(String name) {
        return Arrays.stream(values())
                .filter(i -> i.name.equals(name))
                .findAny()
                .orElse(SIMPLE);
    }

    private static int updateSellInSimple(int sellIn) {
        return sellIn - QUALITY_CHANGE_STEP;
    }

    private static int updateSellInSulfuras(int sellIn) {
        return sellIn;
    }

    private static int updateQualitySimple(int quality, int sellIn) {
        int updatedQuality = isNotExpired(sellIn) ?
                quality - QUALITY_CHANGE_STEP :
                quality - QUALITY_CHANGE_DOUBLE_STEP;

        return updatedQuality > MIN_QUALITY ? updatedQuality : MIN_QUALITY;
    }

    private static int updateQualityBrie(int quality, int sellIn) {
        int updatedQuality = isNotExpired(sellIn) ?
                quality + QUALITY_CHANGE_STEP :
                quality + QUALITY_CHANGE_DOUBLE_STEP;

        return updatedQuality < MAX_QUALITY ? updatedQuality : MAX_QUALITY;
    }

    private static int updateQualitySulfuras(@SuppressWarnings("unused") int quality,
                                             @SuppressWarnings("unused") int sellIn) {
        return SULFURAS_QUALITY;
    }

    private static int updateQualityTicket(int quality, int sellIn) {
        int updatedQuality;
        if (sellIn > TICKET_SELL_IN_DAYS_STEP_1) {
            updatedQuality = quality + QUALITY_CHANGE_STEP;
        } else if (sellIn > TICKET_SELL_IN_DAYS_STEP_2) {
            updatedQuality = quality + QUALITY_CHANGE_DOUBLE_STEP;
        } else if (isNotExpired(sellIn)) {
            updatedQuality = quality + MAX_TICKET_QUALITY_CHANGE_STEP;
        } else {
            updatedQuality = MIN_QUALITY;
        }
        return updatedQuality < MAX_QUALITY ? updatedQuality : MAX_QUALITY;
    }

    private static int updateQualityConjured(int quality, int sellIn) {
        int updatedQuality = isNotExpired(sellIn) ?
                quality - QUALITY_CHANGE_STEP * CONJURED_ITEM_DEGRADE_RATE :
                quality - QUALITY_CHANGE_DOUBLE_STEP * CONJURED_ITEM_DEGRADE_RATE;
        return updatedQuality > MIN_QUALITY ? updatedQuality : MIN_QUALITY;
    }

    private static boolean isNotExpired(int sellIn) {
        return sellIn > 0;
    }

}
