package com.gildedrose.item;

import com.gildedrose.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class ItemConverter {
    private static final Logger log = LoggerFactory.getLogger(ItemConverter.class);
    private static final Set<String> LEGENDARY_ITEMS = Set.of(
        "Sulfuras, Hand of Ragnaros"
    );

    private static final Map<String, Integer> APPRECIATING_ITEMS = Map.of(
        "Aged Brie", 1
    );

    private static final Set<String> LIMITED_TIME_ITEMS = Set.of(
        "Backstage passes to a TAFKAL80ETC concert"
    );

    public GildedRoseItem convert(final Item legacyItem) {
        var name = legacyItem.name;
        log.debug("Converting item '{}'", name);
        if (LEGENDARY_ITEMS.contains(name)) {
            log.debug("Item '{}' is a legendary item", name);
            return new LegendaryItem(legacyItem);
        }

        if (LIMITED_TIME_ITEMS.contains(name)) {
            log.debug("Item '{}' is a limited time item", name);
            return new LimitedTimeItem(legacyItem);
        }

        if (APPRECIATING_ITEMS.containsKey(name)) {
            log.debug("Item '{}' is an appreciating item", name);
            return new AppreciatingItem(legacyItem, APPRECIATING_ITEMS.get(name));
        }

        log.debug("Item '{}' is a normal item", name);
        return new DepreciatingItem(legacyItem);
    }

}
