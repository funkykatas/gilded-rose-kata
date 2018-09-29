package com.gildedrose;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

class GildedRose {

    Item[] items;

    enum ItemType {

        SIMPLE("", ItemType::updateSellInSimple, ItemType::updateQualitySimple),
        BRIE("Aged Brie", ItemType::updateSellInSimple, ItemType::updateQualityBrie),
        SULFURAS("Sulfuras, Hand of Ragnaros", ItemType::updateSellInSulfuras, ItemType::updateQualitySulfuras),
        TICKET("Backstage passes to a TAFKAL80ETC concert", ItemType::updateSellInSimple, ItemType::updateQualityTicket),
        CONJURED("Conjured Mana Cake", ItemType::updateSellInSimple, ItemType::updateQualityConjured);

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

        void updateItem(Item item) {
            item.quality = updateQualityFunction.apply(item.quality, item.sellIn);
            item.sellIn = updateSellInFunction.apply(item.sellIn);
        }

        static ItemType findTypeByName(String name) {
            return Arrays.stream(values())
                    .filter(i -> i.name.equals(name))
                    .findAny()
                    .orElse(SIMPLE);
        }

        private static int updateSellInSimple(int sellIn) {
            return sellIn - 1;
        }

        private static int updateSellInSulfuras(int sellIn) {
            return sellIn;
        }

        private static int updateQualitySimple(int quality, int sellIn) {
            int updatedQuality = sellIn > 0 ? quality - 1 : quality - 2;
            return updatedQuality > 0 ? updatedQuality : 0;
        }

        private static int updateQualityBrie(int quality, int sellIn) {
            int updatedQuality = sellIn > 0 ? quality + 1 : quality + 2;
            return updatedQuality < 50 ? updatedQuality : 50;
        }

        private static int updateQualitySulfuras(@SuppressWarnings("unused") int quality,
                                                 @SuppressWarnings("unused") int sellIn) {
            return 80;
        }

        private static int updateQualityTicket(int quality, int sellIn) {
            int updatedQuality;
            if (sellIn > 10) {
                updatedQuality = quality + 1;
            } else if (sellIn > 5) {
                updatedQuality = quality + 2;
            } else if (sellIn > 0) {
                updatedQuality = quality + 3;
            } else {
                updatedQuality = 0;
            }
            return updatedQuality < 50 ? updatedQuality : 50;
        }

        private static int updateQualityConjured(int quality, int sellIn) {
            int updatedQuality = sellIn > 0 ? quality - 2 : quality - 4;
            return updatedQuality > 0 ? updatedQuality : 0;
        }

    }

    GildedRose(Item[] items) {
        this.items = items;
    }

    void updateItems() {
        Arrays.stream(items).forEach(i -> ItemType.findTypeByName(i.name).updateItem(i));
    }

}