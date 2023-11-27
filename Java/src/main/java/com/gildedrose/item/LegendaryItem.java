package com.gildedrose.item;

import com.gildedrose.Item;

public class LegendaryItem extends GildedRoseItem {
    public static final int QUALITY = 80;

    public LegendaryItem(Item item) {
        super(item.name, item.sellIn, QUALITY);
    }

    /**
     * Legendary items do not degrade in quality, so this method does nothing
     */
    @Override
    public void updateQuality() {
    }

    /**
     * Legendary items don't expire, so this method does nothing
     */
    @Override
    public void decreaseSellIn() {
    }
}
