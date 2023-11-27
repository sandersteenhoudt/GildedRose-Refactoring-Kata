package com.gildedrose;

import com.gildedrose.item.GildedRoseItem;
import com.gildedrose.item.ItemConverter;
import org.slf4j.Logger;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

class GildedRose {
    private static final Logger log = getLogger(ItemConverter.class);
    Item[] items;

    private ItemConverter itemConverter = new ItemConverter();


    public GildedRose(Item[] items) {
        this.items = Arrays.stream(items)
            .map(itemConverter::convert)
            .toArray(Item[]::new);
        log.info("Started Gilded Rose with items: {}", items);
    }

    public void updateQuality() {
        for (Item item : items) {
            ((GildedRoseItem) item).updateQuality();
            ((GildedRoseItem) item).decreaseSellIn();
        }
    }
}
