package com.gildedrose;

public abstract class GildedRoseItem extends Item {

    public GildedRoseItem(final Item item) {
        super(item.name, item.sellIn, item.quality);
    }

    public void updateQuality() {
        quality += getQualityChange();
    }

    protected abstract int getQualityChange();

    /**
     * Update the {@code sellIn} property. By default, it is incremented by one day.
     */
    protected void updateSellInDays() {
        sellIn -= 1;
    }
}
