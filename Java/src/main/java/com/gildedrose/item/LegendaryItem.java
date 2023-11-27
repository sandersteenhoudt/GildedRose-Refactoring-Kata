package com.gildedrose.item;

import com.gildedrose.Item;

/**
 * Legendary items do not degrade in quality
 */
public class LegendaryItem extends GildedRoseItem {
    public static final int QUALITY = 80;

    public LegendaryItem(Item item) {
        super(item, 0);
    }

    @Override
    protected int maximumQuality() {
        return QUALITY;
    }
}
