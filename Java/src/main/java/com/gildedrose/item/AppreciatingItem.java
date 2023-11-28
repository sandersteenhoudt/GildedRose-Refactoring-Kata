package com.gildedrose.item;

import com.gildedrose.Item;

public class AppreciatingItem extends GildedRoseItem {
    public static final int MAXIMUM_QUALITY = 50;
    private final int dailyQualityChange;

    public AppreciatingItem(Item item) {
        this(item, DEFAULT_DAILY_QUALITY_CHANGE);
    }

    public AppreciatingItem(Item item, int dailyQualityChange) {
        super(item);
        this.dailyQualityChange = dailyQualityChange;
    }

    public AppreciatingItem(String name, int sellIn, int quality, int dailyQualityChange) {
        super(name, sellIn, quality);
        this.dailyQualityChange = dailyQualityChange;
    }

    @Override
    public void updateQuality() {
        quality = Integer.min(quality + dailyQualityChange(), maximumQuality());
    }

    protected int dailyQualityChange() {
        return dailyQualityChange;
    }

    /**
     * The maximum quality this item can have. Legendary items have a higher quality than the default maximum.
     *
     * @return maximum quality
     * @see LegendaryItem
     */
    protected int maximumQuality() {
        return MAXIMUM_QUALITY;
    }

}
