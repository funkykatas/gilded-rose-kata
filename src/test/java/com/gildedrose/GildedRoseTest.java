package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gildedrose.ItemFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class GildedRoseTest {

    @Test
    void updateQuality_arrayOrderNotAltered() {
        Item simpleItem = simpleItem(1, 1);
        Item sulfuras = sulfuras(1, 1);
        GildedRose app = new GildedRose(new Item[] { simpleItem, sulfuras });
        app.updateItems();
        assertEquals(simpleItem.name, app.items[0].name);
        assertEquals(sulfuras.name, app.items[1].name);
    }

    @Test
    void updateQuality_emptyArrayNoException() {
        GildedRose app = new GildedRose(new Item[] {});
        app.updateItems();
    }

    @ParameterizedTest
    @MethodSource("createTestItems")
    void updateQuality_OneSizeArrayUpdate(Item beforeUpdate, Item afterUpdateExpected) {

        GildedRose app = new GildedRose(new Item[] { beforeUpdate });
        app.updateItems();
        Item afterUpdateActual = app.items[0];

        assertItemEquals(afterUpdateExpected, afterUpdateActual);
    }

    private static Stream<Arguments> createTestItems() {
        return Stream
                .of(

                        createSimpleItemArguments(),
                        createBrieArguments(),
                        createSulfurasArguments(),
                        createTicketArguments(),
                        createConjuredArguments()

                ).reduce(Stream::concat)
                .orElseGet(Stream::empty);
    }

    private static Stream<Arguments> createSimpleItemArguments() {
        return Stream.of(
                of(simpleItem(10, 10), simpleItem(9, 9)),
                of(simpleItem(10, 0), simpleItem(9, 0)),
                of(simpleItem(1, 1), simpleItem(0, 0)),
                of(simpleItem(0, 2), simpleItem(-1, 0)),
                of(simpleItem(-1, 1), simpleItem(-2, 0)),
                of(simpleItem(-1, 4), simpleItem(-2, 2))
        );
    }

    private static Stream<Arguments> createBrieArguments() {
        return Stream.of(
                of(brie(10, 1), brie(9, 2)),
                of(brie(10, 50), brie(9, 50)),
                of(brie(0, 10), brie(-1, 12)),
                of(brie(-1, 10), brie(-2, 12))
        );
    }

    private static Stream<Arguments> createSulfurasArguments() {
        return Stream.of(
                of(sulfuras(10, 80), sulfuras(10, 80)),
                of(sulfuras(-1, 80), sulfuras(-1, 80))
        );
    }

    private static Stream<Arguments> createTicketArguments() {
        return Stream.of(
                of(ticket(0, 10), ticket(-1, 0)),
                of(ticket(-1, 10), ticket(-2, 0)),
                of(ticket(1, 10), ticket(0, 13)),
                of(ticket(1, 49), ticket(0, 50)),
                of(ticket(10, 10), ticket(9, 12)),
                of(ticket(10, 49), ticket(9, 50)),
                of(ticket(11, 10), ticket(10, 11)),
                of(ticket(11, 50), ticket(10, 50))
        );
    }

    private static Stream<Arguments> createConjuredArguments() {
        return Stream.of(
                of(conjured(10, 10), conjured(9, 8)),
                of(conjured(0, 10), conjured(-1, 6))
        );
    }

    private void assertItemEquals(Item afterUpdateExpected, Item afterUpdateActual) {
        assertEquals(afterUpdateExpected.quality, afterUpdateActual.quality);
        assertEquals(afterUpdateExpected.sellIn, afterUpdateActual.sellIn);
        assertEquals(afterUpdateExpected.name, afterUpdateActual.name);
    }

}
