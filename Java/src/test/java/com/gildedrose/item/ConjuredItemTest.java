package com.gildedrose.item;

import com.gildedrose.assertions.ItemAssertions;
import com.gildedrose.testfixtures.ItemMother;
import org.junit.jupiter.api.Test;

class ConjuredItemTest {
    @Test
    void shouldDegradeTwiceAsFastAsNormalDepreciatingItem() {
        var conjuredItem = new ConjuredItem(ItemMother.conjuredManaCake());
        conjuredItem.updateQuality();
        ItemAssertions.assertThat(conjuredItem).hasQuality(ItemMother.CONJURED_MANA_CAKE_QUALITY - 2);
    }

    @Test
    void shouldHaveQualityOfZeroInsteadOfNegative() {
        var itemWithBelowZeroQualityDegradation = new ConjuredItem(ItemMother.conjuredManaCake(), ItemMother.CONJURED_MANA_CAKE_QUALITY + 1);
        itemWithBelowZeroQualityDegradation.updateQuality();
        ItemAssertions.assertThat(itemWithBelowZeroQualityDegradation).hasQuality(0);
    }
}
