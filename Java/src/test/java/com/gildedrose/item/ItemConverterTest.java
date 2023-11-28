package com.gildedrose.item;

import com.gildedrose.testfixtures.ItemMother;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemConverterTest {
    private final ItemConverter target = new ItemConverter();

    @Test
    void shouldConvertSulfurasToLegendaryItem() {
        assertThat(target.convert(ItemMother.sulfuras()))
            .isInstanceOf(LegendaryItem.class);
    }

    @Test
    void shouldConvertAgedBrieToAppreciatingItem() {
        assertThat(target.convert(ItemMother.agedBrie()))
            .isInstanceOf(AppreciatingItem.class);
    }

    @Test
    void shouldConvertBackstagePassesToLimitedTimeItem() {
        assertThat(target.convert(ItemMother.backstagePass()))
            .isInstanceOf(LimitedTimeItem.class);
    }

    @Test
    void shouldConvertVestToDepreciatingItem() {
        assertThat(target.convert(ItemMother.dexterityVest()))
            .isInstanceOf(DepreciatingItem.class);
    }

    @Test
    void shouldConvertItemWithNameStartingWithConjuredToConjuredItem() {
        assertThat(target.convert(ItemMother.conjuredManaCake())).isInstanceOf(ConjuredItem.class);
    }
}
