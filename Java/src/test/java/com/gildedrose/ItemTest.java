package com.gildedrose;

import com.gildedrose.assertions.ItemAssertions;
import com.gildedrose.testfixtures.ItemMother;
import org.junit.jupiter.api.Test;


class ItemTest {

    @Test
    void shouldSetName() {
        ItemAssertions.assertThat(new Item(ItemMother.VEST_NAME, 5, 20)).hasName(ItemMother.VEST_NAME);
    }

    @Test
    void shouldSetSellIn() {
        ItemAssertions.assertThat(new Item(ItemMother.VEST_NAME, 5, 20)).hasDaysLeftToSell(5);
    }

    @Test
    void shouldSetQuality() {
        ItemAssertions.assertThat(new Item(ItemMother.VEST_NAME, 5, 20)).hasQuality(20);
    }

}
