package com.gildedrose.item;

import com.gildedrose.Item;


public class GildedRoseItem extends Item {
    public static final int DEFAULT_DAILY_QUALITY_CHANGE = -1;
    public static final int MAXIMUM_QUALITY = 50;
    private final int dailyQualityChange;

    public GildedRoseItem(final Item item) {
        this(item.name, item.sellIn, item.quality, DEFAULT_DAILY_QUALITY_CHANGE);
    }

    public GildedRoseItem(final Item item, int dailyQualityChange) {
        this(item.name, item.sellIn, item.quality, dailyQualityChange);
    }

    public GildedRoseItem(String name, int sellIn, int quality, int dailyQualityChange) {
        super(name, sellIn, quality);
        this.dailyQualityChange = dailyQualityChange;
    }

    public void updateQuality() {
        quality = Integer.min(
            Integer.max(quality + dailyQualityChange(), 0),
            maximumQuality()
        );
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

    /**
     * Returns how much the quality should change, negative or positive.
     *
     * @return positive or negative integer
     */
    public int dailyQualityChange() {
        return dailyQualityChange;
    }


    /**
     * Update the {@code sellIn} property. By default, it is decremented by one day.
     */
    public void updateSellInDays() {
        sellIn -= 1;
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
