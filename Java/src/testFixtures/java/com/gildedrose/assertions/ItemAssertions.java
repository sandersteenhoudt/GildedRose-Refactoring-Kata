package com.gildedrose.assertions;

import com.gildedrose.Item;
import org.assertj.core.api.AbstractAssert;

public class ItemAssertions extends AbstractAssert<ItemAssertions, Item> implements EqualityAssertions {
    protected ItemAssertions(Item item) {
        super(item, ItemAssertions.class);
    }

    public static ItemAssertions assertThat(Item item) {
        return new ItemAssertions(item);
    }

    public ItemAssertions hasName(final String expectedName) {
        constructEqualityAssertion("name", expectedName, actual.name);
        return this;
    }

    public ItemAssertions hasDaysLeftToSell(final int daysLeftToSell) {
        constructEqualityAssertion("days left to sell", daysLeftToSell, actual.sellIn);
        return this;
    }

    public ItemAssertions hasQuality(final int expectedQuality) {
        constructEqualityAssertion("quality", expectedQuality, actual.quality);
        return this;
    }

}
