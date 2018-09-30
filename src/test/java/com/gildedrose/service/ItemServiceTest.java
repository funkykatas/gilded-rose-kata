package com.gildedrose.service;

import com.gildedrose.domain.item.Item;
import com.gildedrose.domain.item.ItemRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.stream.Stream;

import static com.gildedrose.util.ItemFactory.*;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    @SuppressWarnings("unused")
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @ParameterizedTest
    @MethodSource("createTestItems")
    void updateQuality_OneSizeArrayUpdate(Item beforeUpdate, Item afterUpdateExpected) {
        when(itemService.getAllItems()).thenReturn(Collections.singletonList(beforeUpdate));
        itemService.updateItems();
        verify(itemRepository).saveAll(Collections.singletonList(afterUpdateExpected));
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

}
