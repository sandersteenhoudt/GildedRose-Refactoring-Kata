package com.gildedrose.item;

import com.gildedrose.testfixtures.ItemMother;
import org.junit.jupiter.api.Test;

import static com.gildedrose.assertions.ItemAssertions.assertThat;

class LegendaryItemTest {
    @Test
    void shouldNotDegradeInQuality() {
        var item = new LegendaryItem(ItemMother.sulfuras());
        item.updateQuality();
        assertThat(item).hasQuality(80);
    }

}
