package com.gildedrose.item;

import com.gildedrose.Item;

public class ConjuredItem extends DepreciatingItem {
    public static final String NAME_PREFIX = "Conjured";
    public static final int QUALITY_DECREMENT_MULTIPLIER = 2;

    public ConjuredItem(Item item) {
        super(item);
    }

    public ConjuredItem(Item item, int dailyQualityChange) {
        super(item, dailyQualityChange);
    }

    @Override
    protected int dailyQualityChange() {
        return super.dailyQualityChange() * QUALITY_DECREMENT_MULTIPLIER;
    }
}
