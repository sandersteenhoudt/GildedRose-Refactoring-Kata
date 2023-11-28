package com.gildedrose.item;

import com.gildedrose.Item;


public class DepreciatingItem extends GildedRoseItem {
    public static final int MINIMUM_QUALITY = 0;
    public static final int EXPIRATION_QUALITY_DECREMENT_MULTIPLIER = 2;

    private final int dailyQualityChange;

    public DepreciatingItem(final Item item) {
        this(item.name, item.sellIn, item.quality, DEFAULT_DAILY_QUALITY_CHANGE);
    }

    public DepreciatingItem(final Item item, int dailyQualityChange) {
        this(item.name, item.sellIn, item.quality, dailyQualityChange);
    }

    public DepreciatingItem(String name, int sellIn, int quality, int dailyQualityChange) {
        super(name, sellIn, quality);
        this.dailyQualityChange = dailyQualityChange;
    }

    protected int dailyQualityChange() {
        return dailyQualityChange;
    }

    /**
     * Depreciating items lose quality every day. This drop in quality is {@linkplain #EXPIRATION_QUALITY_DECREMENT_MULTIPLIER multiplied} when the item has {@linkplain #isExpired() expired}.
     */
    public void updateQuality() {
        var qualityDecrement = isExpired() ? dailyQualityChange() * EXPIRATION_QUALITY_DECREMENT_MULTIPLIER : dailyQualityChange();
        quality = Integer.max(quality - qualityDecrement, MINIMUM_QUALITY);
    }

    @Override
    public String toString() {
        return "GildedRoseItem{" +
            "dailyQualityChange=" + dailyQualityChange +
            ", name='" + name + '\'' +
            ", sellIn=" + sellIn +
            ", quality=" + quality +
            '}';
    }
}
