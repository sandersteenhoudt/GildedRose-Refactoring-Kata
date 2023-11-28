package com.gildedrose.item;

import com.gildedrose.Item;

public abstract class GildedRoseItem extends Item {

    public static final int DEFAULT_DAILY_QUALITY_CHANGE = 1;

    public GildedRoseItem(final Item item) {
        this(item.name, item.sellIn, item.quality);
    }

    public GildedRoseItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void decreaseSellIn() {
        sellIn -= 1;
    }

    @Override
    public String toString() {
        return "GildedRoseItem{" +
            "name='" + name + '\'' +
            ", sellIn=" + sellIn +
            ", quality=" + quality +
            '}';
    }

    protected boolean isExpired() {
        return sellIn < 0;
    }

    public abstract void updateQuality();


}
